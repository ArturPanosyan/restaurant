package am.developers.reportingservice.repository;

import am.developers.reportingservice.model.SaleData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SaleDataSearchRepository extends ElasticsearchRepository<SaleData, Long> {
    List<SaleData> findByProductId(Long productId);
}
