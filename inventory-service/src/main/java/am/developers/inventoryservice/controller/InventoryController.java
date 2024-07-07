package am.developers.inventoryservice.controller;

import am.developers.inventoryservice.entity.Inventory;
import am.developers.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public Inventory updateInventory(@RequestBody Inventory inventory) {
        return inventoryService.updateInventory(inventory);
    }

    @GetMapping("/menu-item/{menuItemId}")
    public Inventory getInventoryByMenuItemId(@PathVariable Long menuItemId) {
        return inventoryService.getInventoryByMenuItemId(menuItemId);
    }

    @PostMapping
    public Inventory addItem(@RequestBody Inventory inventory) {
        return inventoryService.addItem(inventory);
    }

    @GetMapping
    public List<Inventory> getAllItems() {
        return inventoryService.getAllItems();
    }

    @PutMapping("/{itemId}/quantity")
    public Inventory updateItemQuantity(@PathVariable Long itemId, @RequestParam Integer quantity) {
        return inventoryService.updateItemQuantity(itemId, quantity);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        inventoryService.deleteItem(itemId);
    }

    @GetMapping("/search")
    public List<Inventory> searchByItemName(@RequestParam String itemName) {
        return inventoryService.searchByItemName(itemName);
    }

}
