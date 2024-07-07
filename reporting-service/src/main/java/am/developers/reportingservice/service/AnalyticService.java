package am.developers.reportingservice.service;

import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticService {

    private ElasticsearchTemplate elasticsearchTemplate;
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String ANALYTICS_TOPIC = "analytics_events";

    public void trackEvent(Event event) {
        // Логика отслеживания аналитических событий
        kafkaTemplate.send(ANALYTICS_TOPIC, event);
    }

    @KafkaListener(topics = ANALYTICS_TOPIC, groupId = "analytics_group")
    public void processAnalyticsEvent(Event event) {
        // Логика обработки аналитических событий
    }
}
