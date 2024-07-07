package am.developers.menuservice.service;

import am.developers.menuservice.entity.Category;
import am.developers.menuservice.entity.MenuItem;
import am.developers.menuservice.repository.CategoryRepository;
import am.developers.menuservice.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final KafkaTemplate<String, MenuItem> kafkaTemplate;
    private RedisTemplate<String, Object> redisTemplate;
    private static final String TOPIC = "menu";

    @Value("${kafka.topic.menu}")
    private String menuTopic;
    private static final String MENU_CACHE = "MenuCache";


    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getMenuItemsByCategory(Category category) {
        return (Category) menuItemRepository.findAllByCategory(category);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "menuItems", allEntries = true)
    public MenuItem addMenuItem(MenuItem menu) {
        MenuItem savedMenu = menuItemRepository.save(menu);
        kafkaTemplate.send(menuTopic, "Add", savedMenu);
        redisTemplate.opsForHash().put(MENU_CACHE, savedMenu.getId(), savedMenu);
        redisTemplate.expire(MENU_CACHE, 10, TimeUnit.MINUTES);
        return savedMenu;
    }

    @Cacheable("menuItems")
    public List<MenuItem> getAllMenuItems() {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(MENU_CACHE))) {
            Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(MENU_CACHE);
            return hashEntries.values().stream()
                    .map(entry -> (MenuItem) entry)
                    .collect(Collectors.toList());
        }
        List<MenuItem> menuList = menuItemRepository.findAll();
        menuList.forEach(item -> redisTemplate.opsForHash().put(MENU_CACHE, item.getId(), item));
        return menuList;
    }

    public MenuItem updateMenuItem(Long itemId, MenuItem menuDetails) {
        MenuItem menu = menuItemRepository.findById(itemId).orElseThrow();
        menu.setName(menuDetails.getName());
        menu.setPrice(menuDetails.getPrice());
        MenuItem updatedMenu = menuItemRepository.save(menu);
        kafkaTemplate.send(menuTopic, "Update", updatedMenu);
        redisTemplate.opsForHash().put(MENU_CACHE, updatedMenu.getId(), updatedMenu);
        return updatedMenu;
    }

    public void deleteMenuItem(Long itemId) {
        Optional<MenuItem> optionalMenu = menuItemRepository.findById(itemId);
        menuItemRepository.deleteById(itemId);
        optionalMenu.ifPresent(menu -> kafkaTemplate.send(menuTopic, "Delete", menu));
        redisTemplate.opsForHash().delete(MENU_CACHE, itemId);
    }
}
