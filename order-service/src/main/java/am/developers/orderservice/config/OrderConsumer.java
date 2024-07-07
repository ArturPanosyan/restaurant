package am.developers.orderservice.config;

import am.developers.orderservice.model.Order;
import am.developers.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "orders", groupId = "group_id")
    public void consume(Order order) {
        // Логика обработки полученного заказа из Kafka
        orderService.processOrder(order);
    }
}