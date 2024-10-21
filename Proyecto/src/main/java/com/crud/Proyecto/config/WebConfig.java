package com.crud.Proyecto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a todas las rutas bajo /api
                .allowedOrigins("http://127.0.0.1:5500") // Permitir desde el frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowCredentials(true) // Permitir credenciales si es necesario
                .allowedHeaders("*"); // Permitir todos los headers (incluyendo X-ApiKey)
    }
}
