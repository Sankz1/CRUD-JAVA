package API.GoogleMaps;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Geocoder {
    private static final Logger logger = LoggerFactory.getLogger(Geocoder.class);
    private static final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?fields=all&inputtype=textquery&key=";

    // La clave API ahora se obtiene desde una variable de entorno
    private final String apiKey;

    public Geocoder() {
        // Recupera la clave API de la variable de entorno
        this.apiKey = System.getenv("GOOGLE_MAPS_API_KEY");
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalStateException("API Key for Google Maps is not set in the environment variables");
        }
    }

    public String GeocodeSync(String query) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String requestUri = GEOCODING_RESOURCE + apiKey + "&input=" + encodedQuery; 
        
        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000))
                .build();
        
        HttpResponse<String> geocodingResponse = httpClient.send(geocodingRequest, HttpResponse.BodyHandlers.ofString());
        
        // Manejo de estado HTTP
        if (geocodingResponse.statusCode() != 200) {
            throw new IOException("Error in response: " + geocodingResponse.statusCode() + " " + geocodingResponse.body());
        }
        
        return geocodingResponse.body();
    }

    public String getLatLng(String query) {
        try {
            // Usa GeocodeSync para obtener la respuesta
            String responseBody = GeocodeSync(query);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode response = objectMapper.readTree(responseBody);
            
            logger.info("Respuesta de la API: {}", responseBody); // Agregar log para la respuesta

            if (response != null && response.has("candidates") && response.get("candidates").isArray()) {
                JsonNode candidates = response.get("candidates");
                
                // Verifica que hay al menos un candidato
                if (candidates.size() > 0) {
                    JsonNode firstCandidate = candidates.get(0); // Obtiene el primer candidato
                    if (firstCandidate.has("geometry") && firstCandidate.get("geometry").has("location")) {
                        JsonNode location = firstCandidate.get("geometry").get("location");
                        double lat = location.get("lat").asDouble();
                        double lng = location.get("lng").asDouble();
                        return lat + "," + lng; // Devuelve la cadena de coordenadas
                    } else {
                        logger.warn("No se encontró 'geometry' o 'location' en el candidato: {}", firstCandidate);
                    }
                } else {
                    logger.warn("No se encontraron candidatos para la consulta: {}", query);
                }
            } else {
                logger.warn("Respuesta nula o estructura inesperada: {}", response);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error al obtener las coordenadas para la consulta {}: {}", query, e.getMessage(), e);
        }
        return null; // Devuelve null si no hay resultados
    }

}
