package com.crud.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ApiKeyController {

    @Value("${GOOGLE_MAPS_API_KEY}")
    private String googleMapsApiKey;

    @GetMapping("/api/key")
    public String getGoogleMapsApiKey() {
        return googleMapsApiKey; // Devuelve la clave API como texto
    }
}
