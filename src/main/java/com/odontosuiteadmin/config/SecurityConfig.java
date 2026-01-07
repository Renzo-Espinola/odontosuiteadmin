package com.odontosuiteadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    // 1) Endpoints públicos (para probar reportes/caja)
//    @Bean
//    @Order(1)
//    SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/api/reports/**", "/api/cash/**", "/actuator/**")
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .build();
//    }
//
//    @Bean
//    @Order(2)
//    SecurityFilterChain securedEndpoints(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        // (opcional) permitir preflight si estás llamando desde browser
//                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .build();
//    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api/reports/**").permitAll()
                        .requestMatchers("/api/cash/**").permitAll()
                        .requestMatchers("/api/appointments/**").permitAll()
                        .anyRequest().authenticated()
                )
                // si no querés JWT por ahora, comentá esto:
                // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
