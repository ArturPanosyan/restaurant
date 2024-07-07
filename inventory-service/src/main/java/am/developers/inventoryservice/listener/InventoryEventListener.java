package am.developers.inventoryservice.listener;

import am.developers.inventoryservice.entity.Inventory;
import am.developers.inventoryservice.repository.InventoryRepository;
import am.developers.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryEventListener {

    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "${kafka.topic.inventory}", groupId = "inventory_group")
    public void listenInventoryEvents(@NotNull String eventType, Object event) {
        switch (eventType) {
            case "Add", "Update":
                inventoryRepository.save((Inventory) event);
                break;
            case "Delete":
                inventoryRepository.deleteById((Long) event);
                break;
        }
    }


}
