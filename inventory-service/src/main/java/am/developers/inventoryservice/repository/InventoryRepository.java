package am.developers.inventoryservice.repository;

import am.developers.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByMenuItemId(Long menuItemId);
    Optional<Inventory> findByProductId(String productId);
}
