package com.crud.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@Controller
public class MapController {

    @Value("${GOOGLE_MAPS_API_KEY}")
    private String googleMapsApiKey;

    @GetMapping("/map")  // Ruta para acceder a la página del mapa
    public String getMapPage(Model model, HttpSession session) {
        String bearerToken = (String) session.getAttribute("BEARER_TOKEN");

        // Verificar si el token es válido
        if (!isBearerTokenValid(bearerToken)) {
            return "redirect:/login";  // Redirige si no hay token
        }

        // Verificar si la API Key es válida
        if (!isApiKeyValid(googleMapsApiKey)) {
            return "error";  // Muestra una página de error si la API Key falta
        }

        // Añadir la clave de la API de Google Maps y el token Bearer al modelo
        model.addAttribute("GOOGLE_MAPS_API_KEY", googleMapsApiKey);
        model.addAttribute("bearerToken", bearerToken);

        return "index";  // Nombre del archivo HTML que Thymeleaf cargará
    }

    private boolean isBearerTokenValid(String bearerToken) {
        return bearerToken != null; // Comprueba si el token no es nulo
    }

    private boolean isApiKeyValid(String apiKey) {
        return apiKey != null && !apiKey.isEmpty(); // Comprueba si la API Key no es nula ni vacía
    }
}

