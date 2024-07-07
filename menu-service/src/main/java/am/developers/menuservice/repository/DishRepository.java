package am.developers.menuservice.repository;

import am.developers.menuservice.entity.Category;
import am.developers.menuservice.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByCategory(String category);
    List<Dish> findByNameContaining(String name);
}
