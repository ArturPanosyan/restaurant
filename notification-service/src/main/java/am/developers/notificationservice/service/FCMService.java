package am.developers.notificationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    @Value("${fcm.server.key}")
    private String fcmServerKey;

    public void sendPushNotification(String token, String title, String body) {
        // Логика отправки Push-уведомлений через FCM
    }

}
