package br.com.compassuol.pb.challenge.ms_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-products", r -> r
                        .path("/products/**")
                        .filters(f -> f.rewritePath("/products/(?<id>.*)", "/products/${id}"))
                        .uri("http://localhost:8080"))
                .build();
    }

}
