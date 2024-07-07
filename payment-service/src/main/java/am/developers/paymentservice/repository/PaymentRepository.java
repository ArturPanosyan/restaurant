package am.developers.paymentservice.repository;

import am.developers.paymentservice.entity.Payment;
import am.developers.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
}
