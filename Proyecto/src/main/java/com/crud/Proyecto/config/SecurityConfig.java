package com.crud.Proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtils.JwtRequestFilter jwtRequestFilter) throws Exception {
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permitir solicitudes OPTIONS
            .requestMatchers("/usuarios/authenticate").permitAll()
            .requestMatchers("/api/key").permitAll()
            .requestMatchers("/personas").permitAll()
            .requestMatchers("/swagger-ui/index.html").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()  // OpenAPI docs
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/Coordenadas/**").permitAll()
            .anyRequest().authenticated()
        )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin estado para la API REST
            );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agrega el filtro JWT antes del filtro de autenticación

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir CORS para todos los endpoints
                        .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500") // URLs permitidas
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
