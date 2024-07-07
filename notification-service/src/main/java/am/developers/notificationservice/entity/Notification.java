package am.developers.notificationservice.entity;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    private String message;
    private Date date;
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;
    private Long orderId;
    private String recipient;
    private Boolean sent;
    private LocalDateTime createdAt;
    private Boolean read;
    private LocalDateTime timestamp;
    private Long userId;

}