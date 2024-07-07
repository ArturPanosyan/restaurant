package am.developers.paymentservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long userId;
    private Double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}