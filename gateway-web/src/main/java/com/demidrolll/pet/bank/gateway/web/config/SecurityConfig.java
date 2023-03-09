package com.demidrolll.pet.bank.gateway.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeHttpRequests ->
            authorizeHttpRequests
                .requestMatchers("/articles/**")
                .hasAuthority("SCOPE_articles.read")
        )
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }
}
