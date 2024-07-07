package am.developers.notificationservice.service;

import am.developers.notificationservice.entity.Notification;
import am.developers.notificationservice.entity.NotificationStatus;
import am.developers.notificationservice.entity.NotificationType;
import am.developers.notificationservice.repository.NotificationRepository;
import am.developers.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, Notification> kafkaTemplate;
    private final JavaMailSender mailSender;
    private static final String EMAIL_TOPIC = "email_notifications";
    private static final String SMS_TOPIC = "sms_notifications";
    private static final String TOPIC = "notifications";

    @Value("${kafka.topic.notification}")
    private String notificationTopic;

    @KafkaListener(topics = "orders", groupId = "notification_group")
    public void handleOrderNotification(String message) {
        System.out.println("Received Order Notification: " + message);
        // логика для отправки уведомлений
    }

    @KafkaListener(topics = "menu", groupId = "notification_group")
    public void handleMenuNotification(String message) {
        System.out.println("Received Menu Notification: " + message);
        // логика для отправки уведомлений
    }


    public Notification createNotification(Notification notification) {
        Notification savedNotification = notificationRepository.save(notification);
        kafkaTemplate.send(TOPIC, savedNotification);
        return savedNotification;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @KafkaListener(topics = "orders", groupId = "notification_group")
    public void listenOrderNotifications(Order order) {
        // Create notification based on order status
        Notification notification = new Notification();
        notification.setNotificationStatus(NotificationStatus.ORDER_STATUS);
        notification.setMessage("Order " + order.getId() + " status: " + order.getStatus());
        notification.setDate(new Date());
        createNotification(notification);
    }

    public Notification sendNotification(String recipient, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setSent(false);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    public void sendEmailNotification(String email, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendSmsNotification(String phoneNumber, String message) {
        // Logic to send SMS notification
    }

    @KafkaListener(topics = EMAIL_TOPIC, groupId = "notification_group")
    public void listenEmailNotifications(String email, String message) {
        sendEmailNotification(email, message);
    }

    @KafkaListener(topics = SMS_TOPIC, groupId = "notification_group")
    public void listenSmsNotifications(String phoneNumber, String message) {
        sendSmsNotification(phoneNumber, message);
    }
}
