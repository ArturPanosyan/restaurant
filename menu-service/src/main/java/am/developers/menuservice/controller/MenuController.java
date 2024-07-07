package am.developers.menuservice.controller;

import am.developers.menuservice.entity.Category;
import am.developers.menuservice.entity.MenuItem;
import am.developers.menuservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/item")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuService.addMenuItem(menuItem);
    }

    @PutMapping("/item/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        return menuService.updateMenuItem(menuItem);
    }

    @DeleteMapping("/item/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
    }

    @GetMapping("/item")
    public List<MenuItem> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    @PostMapping("/category")
    public Category addCategory(@RequestBody Category category) {
        return menuService.addCategory(category);
    }

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return menuService.getAllCategories();
    }


    @GetMapping("/item/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuService.getMenuItemById(id);
    }

    @GetMapping("/items/category/{category}")
    public Category getMenuItemsByCategory(@PathVariable Category category) {
        return menuService.getMenuItemsByCategory(category);
    }

}
