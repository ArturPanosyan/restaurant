package am.developers.menuservice.repository;

import am.developers.menuservice.entity.Category;
import am.developers.menuservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Category findAllByCategory (Category category);
}
