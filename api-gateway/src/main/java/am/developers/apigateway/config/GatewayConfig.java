package am.developers.apigateway.config;

import am.developers.apigateway.filter.CustomGatewayFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final CustomGatewayFilter customGatewayFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(customGatewayFilter.apply(new CustomGatewayFilter.Config())))
                        .uri("http://localhost:8081"))
                .route("order-service", r -> r.path("/orders/**")
                        .filters(f -> f.filter(customGatewayFilter.apply(new CustomGatewayFilter.Config())))
                        .uri("http://localhost:8082"))
                .route("menu-service", r -> r.path("/menu/**")
                        .filters(f -> f.filter(customGatewayFilter.apply(new CustomGatewayFilter.Config())))
                        .uri("http://localhost:8083"))
                .route("notification-service", r -> r.path("/notifications/**")
                        .filters(f -> f.filter(customGatewayFilter.apply(new CustomGatewayFilter.Config())))
                        .uri("http://localhost:8084"))
                .route("config-service", r -> r.path("/configurations/**")
                        .filters(f -> f.filter(customGatewayFilter.apply(new CustomGatewayFilter.Config())))
                        .uri("http://localhost:8085"))
                .build();
    }
}
