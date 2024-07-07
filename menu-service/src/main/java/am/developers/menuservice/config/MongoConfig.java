package am.developers.menuservice.config;


import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "menu_db");
        return mongoTemplate;
    }
}