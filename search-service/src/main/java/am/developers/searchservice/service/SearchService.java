package am.developers.searchservice.service;

import am.developers.searchservice.entity.MenuItemIndex;
import am.developers.searchservice.entity.Product;
import am.developers.searchservice.entity.Search;
import am.developers.searchservice.repository.MenuItemIndexRepository;
import am.developers.searchservice.repository.ProductRepository;
import am.developers.searchservice.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final MenuItemIndexRepository menuItemRepository;
    private final ProductRepository productRepository;
    private final SearchRepository searchRepository;

    @KafkaListener(topics = "menu", groupId = "search_group")
    public void handleMenuItem(String message) {
        // Логика для индексации данных в Elasticsearch
        MenuItemIndex menuItem = convertMessageToMenuItem(message);
        menuItemRepository.save(menuItem);
    }

    private MenuItemIndex convertMessageToMenuItem(String message) {
        // Преобразование сообщения в объект MenuItemIndex
        // Реализуйте здесь необходимую логику
        return null;
    }

    public Product indexProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);
    }

    public List<SearchResult> searchByQuery(String query) {
        return searchRepository.findByQueryContaining(query);
    }
}