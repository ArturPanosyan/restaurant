package am.developers.menuservice.service;

import am.developers.menuservice.entity.Dish;
import am.developers.menuservice.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public List<Dish> getDishesByCategory(String category) {
        return dishRepository.findByCategory(category);
    }

    public List<Dish> searchDishesByName(String name) {
        return dishRepository.findByNameContaining(name);
    }
}
