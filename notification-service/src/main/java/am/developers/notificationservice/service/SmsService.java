package am.developers.notificationservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private RedisTemplate<String, Object> redisTemplate;

    public void sendSms(String phoneNumber, String message) {
        // Логика отправки SMS через Twilio
        // Пример интеграции с Twilio API
    }

    public boolean isNotificationSent(String orderId) {
        Boolean sent = (Boolean) redisTemplate.opsForValue().get(orderId);
        return sent != null && sent;
    }

    public void markNotificationSent(String orderId) {
        redisTemplate.opsForValue().set(orderId, true);
    }
}
