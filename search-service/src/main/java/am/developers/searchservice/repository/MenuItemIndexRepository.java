package am.developers.searchservice.repository;

import am.developers.searchservice.entity.MenuItemIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.awt.*;

public interface MenuItemIndexRepository extends ElasticsearchRepository<MenuItemIndex, String> {
}
