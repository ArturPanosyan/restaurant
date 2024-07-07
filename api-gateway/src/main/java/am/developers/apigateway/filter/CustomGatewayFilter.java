package am.developers.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomGatewayFilter extends AbstractGatewayFilterFactory<CustomGatewayFilter.Config> {

    public CustomGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Реализация вашего фильтра
        return (exchange, chain) -> {
            // Логика фильтрации здесь
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Конфигурационные параметры фильтра
    }
}
