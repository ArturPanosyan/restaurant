package am.developers.paymentservice.service;

import am.developers.paymentservice.entity.Payment;
import am.developers.paymentservice.entity.PaymentStatus;
import am.developers.paymentservice.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String PAYMENT_TOPIC = "payment_notifications";

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${kafka.topic.payment}")
    private String paymentTopic;

    @KafkaListener(topics = "${kafka.topic.payment}", groupId = "payment_group")
    public void listenPaymentEvents(String eventType, Object event) {
        // Process payment events
    }

    @KafkaListener(topics = PAYMENT_TOPIC, groupId = "payment_group")
    public void listenPaymentNotifications(String eventType, Payment payment) {
        switch (eventType) {
            case "Processed":
                // Логика обработки уведомлений о платежах
                break;
            // другие кейсы
        }
    }

    public Payment refundPayment(Long paymentId) {
        // Логика возврата платежа
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Логика возврата платежа
        payment.setStatus(PaymentStatus.REFUNDED);
        Payment refundedPayment = paymentRepository.save(payment);

        kafkaTemplate.send(PAYMENT_TOPIC, "Refunded", refundedPayment);
        return refundedPayment;
    }


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Charge createCharge(String token, double amount) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100)); // amount is in cents
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }

    public void processPayment(String token, double amount) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token);
        chargeParams.put("description", "Order Payment");
        Charge.create(chargeParams);
    }

    public Payment createPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTimestamp(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus(payment.getStatus());
        return paymentRepository.save(payment);
    }

}
