package com.crud.Proyecto.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Paths;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;

@Configuration
@OpenAPIDefinition(
	info = @Info(
				title = "API REST CRUD CON MAPEO",
				description = "API para registrar personas, crear usuarios y por ende mapeo acorde a la ubicacion registrada tambien se implementa metodo de seguridad por tokens y encripatado de claves",
				termsOfService = "www.CIPA4.com/terminos_y_servicios",
				version = "1.0.0",
				contact = @Contact(
						name = "Edwin Devia",
				
						email = "e.devia.b1306@gmail.com"
						)

						
			),
				servers = {
					@Server(
					description = "DEV SERVER",
					url = "http://localhost:8080"
							)
				}	
		)
public class SwaggerConfig {
    @Bean
    public OpenApiCustomizer customiseOpenAPI() {
        return openApi -> {
            Paths paths = new Paths();
            openApi.getPaths().entrySet().stream()
                .filter(path -> path.getKey().startsWith("/Coordenadas") || path.getKey().startsWith("/personas"))
                .forEach(path -> paths.addPathItem(path.getKey(), path.getValue()));
            openApi.setPaths(paths);
        };
    }
}