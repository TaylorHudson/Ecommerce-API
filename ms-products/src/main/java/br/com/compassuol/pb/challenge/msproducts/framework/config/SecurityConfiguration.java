package br.com.compassuol.pb.challenge.msproducts.framework.config;

import br.com.compassuol.pb.challenge.msproducts.framework.filters.JwtAuthenticationFilter;
import br.com.compassuol.pb.challenge.msproducts.framework.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableMethodSecurity
//@SecurityScheme(
//        name = "Bear Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
@AllArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final List<String> allowedMethods = new ArrayList<>();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint));

         http
            .cors(cors -> cors.configurationSource(request -> {
                var config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("*"));
                config.setAllowedMethods(getAllowedMethods());
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));
                config.setMaxAge(3600L);
                return config;
            })
         );

         http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

         http.authorizeHttpRequests(req -> req
                    .requestMatchers("/oauth/validate").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users", "/login").permitAll()
                    .anyRequest().authenticated());
        //                    .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
         return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<String> getAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.name());
        allowedMethods.add(HttpMethod.POST.name());
        allowedMethods.add(HttpMethod.PUT.name());
        allowedMethods.add(HttpMethod.PATCH.name());
        allowedMethods.add(HttpMethod.DELETE.name());

        return allowedMethods;
    }
}
