package am.developers.orderservice.service;

import am.developers.orderservice.model.Order;
import lombok.RequiredArgsConstructor;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderSearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public OrderSearchService(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public void indexOrder(Order order) {
        try {
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(order.getId().toString())
                    .withObject(order)
                    .build();

            IndexCoordinates indexCoordinates = IndexCoordinates.of("orders"); // Укажите здесь имя индекса

            elasticsearchRestTemplate.index(indexQuery, indexCoordinates);
        } catch (Exception e) {
            e.printStackTrace(); // или используйте логирование
        }
    }
}
