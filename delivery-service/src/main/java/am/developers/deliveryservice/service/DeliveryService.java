package am.developers.deliveryservice.service;

import am.developers.deliveryservice.entity.Delivery;
import am.developers.deliveryservice.repoistory.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final RestTemplate restTemplate;

    public Delivery trackDelivery(String orderId) {
        // Логика отслеживания доставки
        return deliveryRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Delivery updateDeliveryStatus(String orderId, String status) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        delivery.setStatus(status);
        return deliveryRepository.save(delivery);
    }

    public void integrateWithCourierService(String orderId) {
        // Логика интеграции с внешним курьерским сервисом
        String url = "https://api.courier.com/track?orderId=" + orderId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        // Обработка ответа
    }
}
