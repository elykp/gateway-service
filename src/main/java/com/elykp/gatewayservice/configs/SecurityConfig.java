package com.elykp.gatewayservice.configs;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Value("${oauth.scope.asset}")
  private String assetScope;

  @Value("${oauth.scope.core}")
  private String coreScope;

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    http.cors().and()
        .csrf().disable()
        .authorizeExchange(exchange -> exchange
                .pathMatchers(HttpMethod.GET, "/actuator/**")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/photos/**", "/api/tags/**")
                .permitAll()
                .pathMatchers("/api/photos/**", "/api/tags/**")
                .hasAuthority(coreScope)
                .pathMatchers("/api/assets/**")
                .hasAuthority(assetScope)
                .anyExchange().denyAll())
        .oauth2ResourceServer(OAuth2ResourceServerSpec::jwt);

    return http.build();
  }

  @Bean
  CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(List.of("".split(",")));
    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    corsConfig.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}
