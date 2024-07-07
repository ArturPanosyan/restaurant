package am.developers.searchservice.repository;

import am.developers.searchservice.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);
}
