package am.developers.reportingservice.service;

import am.developers.reportingservice.model.SaleData;
import am.developers.reportingservice.repository.SaleDataRepository;
import am.developers.reportingservice.repository.SaleDataSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SaleDataService {

    private final SaleDataRepository saleDataRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SALE_DATA_CACHE = "SaleDataCache";
    private final SaleDataSearchRepository saleDataSearchRepository;

    @Value("${kafka.topic.sales}")
    private String salesTopic;

    public SaleData saveSaleData(SaleData saleData) {
        SaleData savedData = saleDataRepository.save(saleData);
        saleDataSearchRepository.save(savedData);
        return savedData;
    }

    public List<SaleData> getSalesByProductId(Long productId) {
        return saleDataSearchRepository.findByProductId(productId);
    }

    public List<SaleData> getSalesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return saleDataRepository.findBySaleDateBetween(startDate, endDate);
    }
}
