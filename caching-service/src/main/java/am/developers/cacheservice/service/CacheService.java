package am.developers.cacheservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
    }

    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @KafkaListener(topics = "menu", groupId = "cache_group")
    public void handleMenuItem(String message) {
        // Логика для кеширования данных в Redis
        MenuItem menuItem = convertMessageToMenuItem(message);
        redisTemplate.opsForValue().set(menuItem.getName(), menuItem);
    }

    private MenuItem convertMessageToMenuItem(String message) {
        // Преобразование сообщения в объект MenuItem
        // Реализуйте здесь необходимую логику
        MenuItem menuItem = new MenuItem();
        return null;
    }

    public void cacheData(String key, Object data, long ttl) {
        redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.SECONDS);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
