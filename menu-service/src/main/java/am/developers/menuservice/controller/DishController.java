package am.developers.menuservice.controller;

import am.developers.menuservice.entity.Dish;
import am.developers.menuservice.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PostMapping("/add")
    public Dish addDish(@RequestBody Dish dish) {
        return dishService.addDish(dish);
    }

    @GetMapping("/category/{category}")
    public List<Dish> getDishesByCategory(@PathVariable String category) {
        return dishService.getDishesByCategory(category);
    }

    @GetMapping("/search")
    public List<Dish> searchDishesByName(@RequestParam String name) {
        return dishService.searchDishesByName(name);
    }
}
