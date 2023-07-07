package br.com.compassuol.pb.challenge.ms_gateway;

import br.com.compassuol.pb.challenge.ms_gateway.dto.AuthResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }

            return webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8100/oauth/token?token=" + parts[1])
                    .retrieve().bodyToMono(AuthResponse.class)
                    .map(response -> {
                        exchange.getRequest()
                                .mutate()
                                .header("X-auth-user-id", String.valueOf(response.getId()));
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public static class Config {
        // empty class as I don't need any particular configuration
    }

}
