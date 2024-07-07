package am.developers.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class GatewayService {

    private RedisTemplate<String, Object> redisTemplate;
    private static final String GATEWAY_CACHE = "GATEWAY_CACHE";

    public ExampleEntity saveEntity(ExampleEntity entity) {
        ExampleEntity savedEntity = exampleRepository.save(entity);
        redisTemplate.opsForHash().put(EXAMPLE_CACHE, savedEntity.getId(), savedEntity);
        redisTemplate.expire(EXAMPLE_CACHE, 10, TimeUnit.MINUTES);
        return savedEntity;
    }

    public ExampleEntity getEntity(Long id) {
        if (redisTemplate.opsForHash().hasKey(EXAMPLE_CACHE, id)) {
            return (ExampleEntity) redisTemplate.opsForHash().get(EXAMPLE_CACHE, id);
        }
        ExampleEntity entity = exampleRepository.findById(id).orElseThrow();
        redisTemplate.opsForHash().put(EXAMPLE_CACHE, id, entity);
        return entity;
    }

}
