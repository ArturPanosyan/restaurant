package am.developers.inventoryservice.service;

import am.developers.inventoryservice.entity.Inventory;
import am.developers.inventoryservice.repository.InventoryRepository;
import am.developers.inventoryservice.repository.InventorySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventorySearchRepository searchRepository;
    private final KafkaTemplate<String, Inventory> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String INVENTORY_CACHE = "InventoryCache";

    @Value("${kafka.topic.inventory}")
    private String inventoryTopic;

    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventoryByMenuItemId(Long menuItemId) {
        return inventoryRepository.findByMenuItemId(menuItemId);
    }

    public Inventory addItem(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        kafkaTemplate.send(inventoryTopic, "Add", savedInventory);
        searchRepository.save(savedInventory);
        redisTemplate.opsForHash().put(INVENTORY_CACHE, savedInventory.getId(), savedInventory);
        redisTemplate.expire(INVENTORY_CACHE, 10, TimeUnit.MINUTES);
        return savedInventory;
    }


    public List<Inventory> getAllItems() {
        // Проверка наличия данных в Redis
        if (Boolean.TRUE.equals(redisTemplate.hasKey(INVENTORY_CACHE))) {
            Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(INVENTORY_CACHE);
            return hashEntries.values().stream()
                    .map(entry -> (Inventory) entry)
                    .collect(Collectors.toList());
        }
        List<Inventory> inventoryList = inventoryRepository.findAll();
        inventoryList.forEach(item -> redisTemplate.opsForHash().put(INVENTORY_CACHE, item.getId(), item));
        return inventoryList;
    }


    public Inventory updateItemQuantity(Long itemId, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(itemId).orElseThrow();
        inventory.setQuantity(quantity);
        Inventory updatedInventory = inventoryRepository.save(inventory);
        kafkaTemplate.send(inventoryTopic, "Update", updatedInventory);
        searchRepository.save(updatedInventory);
        redisTemplate.opsForHash().put(INVENTORY_CACHE, updatedInventory.getId(), updatedInventory);
        return updatedInventory;
    }

    public void deleteItem(Long itemId) {
        // Получаем объект Inventory перед удалением
        Optional<Inventory> optionalInventory = inventoryRepository.findById(itemId);
        inventoryRepository.deleteById(itemId);
        optionalInventory.ifPresent(inventory -> kafkaTemplate.send(inventoryTopic,
                "Delete", inventory));

        searchRepository.deleteById(itemId);
        redisTemplate.opsForHash().delete(INVENTORY_CACHE, itemId);
    }

    public List<Inventory> searchByItemName(String itemName) {
        return searchRepository.findByItemNameContaining(itemName);
    }

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void updateInventory(String orderDetails) {
        // Логика обработки заказа и обновления запасов
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId).orElseThrow(() ->
                new RuntimeException("Product not found"));
    }

}
