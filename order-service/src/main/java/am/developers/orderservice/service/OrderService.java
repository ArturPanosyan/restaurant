package am.developers.orderservice.service;

import am.developers.orderservice.exception.OrderProcessingException;
import am.developers.orderservice.model.Order;
import am.developers.orderservice.model.OrderStatus;
import am.developers.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final String TOPIC = "orders";
    private static final String ORDER_CACHE_KEY = "orders";
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final RedisTemplate<String, List<Order>> redisTemplate;
    @Value("${kafka.topic.order}")
    private String orderTopic;
    private static final String ORDER_CACHE = "OrderCache";

    public Order addOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(orderTopic, "Add", savedOrder);
        redisTemplate.opsForHash().put(ORDER_CACHE, savedOrder.getId(), savedOrder);
        redisTemplate.expire(ORDER_CACHE, 10, TimeUnit.MINUTES);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(ORDER_CACHE))) {
            Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(ORDER_CACHE);
            return hashEntries.values().stream()
                    .map(entry -> (Order) entry)
                    .collect(Collectors.toList());
        }
        List<Order> orderList = orderRepository.findAll();
        orderList.forEach(item -> redisTemplate.opsForHash().put(ORDER_CACHE, item.getId(), item));
        return orderList;
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.valueOf(status));
        Order updatedOrder = orderRepository.save(order);
        kafkaTemplate.send(orderTopic, "Update", updatedOrder);
        redisTemplate.opsForHash().put(ORDER_CACHE, updatedOrder.getId(), updatedOrder);
        return updatedOrder;
    }

    public void deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        orderRepository.deleteById(orderId);
        optionalOrder.ifPresent(order -> kafkaTemplate.send(orderTopic, "Delete", order));
        redisTemplate.opsForHash().delete(ORDER_CACHE, orderId);
    }
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(TOPIC, savedOrder);
        redisTemplate.delete(ORDER_CACHE_KEY);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            // Логика обновления статуса заказа
            return orderRepository.save(order);
        }
        return null;
    }

    @CircuitBreaker(name = "orderService", fallbackMethod = "fallbackMethod")
    public String getOrderDetails(Long orderId) {
        // Логика получения деталей заказа
        if (orderId == null) {
            throw new RuntimeException("OrderId cannot be null");
        }
        return "Order details for orderId: " + orderId;
    }

    public String fallbackMethod(Long orderId, Throwable throwable) {
        return "Fallback response due to: " + throwable.getMessage();
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order processOrder(Order order) {
        try {
            // Логика обработки заказа
            return orderRepository.save(order);
        } catch (Exception e) {
            log.error("Ошибка при обработке заказа: {}", e.getMessage());
            throw new OrderProcessingException("Ошибка при обработке заказа");
        }
    }
}
