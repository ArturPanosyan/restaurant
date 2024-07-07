package am.developers.notificationservice.controller;

import am.developers.notificationservice.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationWebSocketController {

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notification")
    public Notification sendNotification(Notification notification) {
        // Логика отправки уведомления
        return notification;
    }
}