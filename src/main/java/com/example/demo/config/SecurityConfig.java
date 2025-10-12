package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad Spring.
 * 
 * NOTA: Deshabilitamos completamente la autenticación automática de Spring Security
 * porque estamos implementando un sistema de autenticación custom
 * siguiendo las prácticas del space jon-security.
 * 
 * En producción, se debería implementar Spring Security completo con JWT o OAuth2.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF
            .csrf(AbstractHttpConfigurer::disable)
            // Deshabilitar CORS
            .cors(AbstractHttpConfigurer::disable)
            // Permitir todas las requests
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // Deshabilitar HTTP Basic Auth
            .httpBasic(AbstractHttpConfigurer::disable)
            // Deshabilitar form login
            .formLogin(AbstractHttpConfigurer::disable)
            // Deshabilitar logout
            .logout(AbstractHttpConfigurer::disable)
            // Deshabilitar session management
            .anonymous(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
}
