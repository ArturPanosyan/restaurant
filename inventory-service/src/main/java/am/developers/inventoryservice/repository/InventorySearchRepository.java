package am.developers.inventoryservice.repository;

import am.developers.inventoryservice.entity.Inventory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface InventorySearchRepository extends ElasticsearchRepository<Inventory, Long> {
    List<Inventory> findByItemNameContaining(String itemName);
}
