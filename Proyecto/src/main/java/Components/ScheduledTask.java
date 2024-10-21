package Components;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.crud.Proyecto.Entities.Coordenadas;
import com.crud.Proyecto.Entities.Persona;
import com.crud.Proyecto.Repository.CoordenadasRepository;
import com.crud.Proyecto.Repository.PersonaRepository;

import API.GoogleMaps.Geocoder;
@Component
public class ScheduledTask {
    private static final Logger Logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private PersonaRepository IPersonaRepository;

    @Autowired
    private CoordenadasRepository ICoordenadaRepository;

    @Scheduled(cron = "0 */3 * * * ?")
    public void scheduleTaskWithCronExpression() {
    	
        Logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalTime.now()));
        
        try {
            List<Persona> listPersonas = IPersonaRepository.findAll();
            if (listPersonas != null && !listPersonas.isEmpty()) {
                Geocoder geocoder = new Geocoder();
                for (Persona persona : listPersonas) {
                    // Verificamos que la persona tenga una ubicación antes de consultar el Geocoder
                    if (persona.getUbicacion() == null || persona.getUbicacion().isEmpty()) {
                        Logger.warn("Ubicación no especificada para: {}", persona.getPnombre());
                        continue; // Salta esta iteración y pasa a la siguiente
                    }

                    String LatLng = geocoder.getLatLng(persona.getUbicacion());
                    
                    if (LatLng == null || LatLng.isEmpty()) {
                        Logger.warn("No se obtuvieron coordenadas para: {}", persona.getPnombre());
                        continue; // Salta esta iteración y pasa a la siguiente
                    }
                    
                    String[] coor = LatLng.split(",");
                    Logger.info("Coordenadas obtenidas para {}: {}", persona.getPnombre(), LatLng);
                    
                    // Obtener todas las coordenadas asociadas a la persona
                    List<Coordenadas> coordenadasList = ICoordenadaRepository.getCoordenadaXPersona(persona.getId());
                    
                    if (coordenadasList == null || coordenadasList.isEmpty()) {
                        // Si no existen coordenadas, se crean nuevas
                        ICoordenadaRepository.save(new Coordenadas(persona.getId(), persona.getPnombre(),
                            Double.parseDouble(coor[0]), Double.parseDouble(coor[1])));
                        Logger.info("Coordenadas guardadas para: {}", persona.getPnombre());
                    } else {
                        // Si ya existen coordenadas, puedes decidir si actualizas o no.
                        Coordenadas ultimaCoordenada = coordenadasList.get(coordenadasList.size() - 1);
                        
                        // Verificar si las coordenadas han cambiado
                        double nuevaLatitud = Double.parseDouble(coor[0]);
                        double nuevaLongitud = Double.parseDouble(coor[1]);

                        if (ultimaCoordenada.getLatitud() != nuevaLatitud || ultimaCoordenada.getLongitud() != nuevaLongitud) {
                            // Si las coordenadas han cambiado, guarda las nuevas
                            ICoordenadaRepository.save(new Coordenadas(persona.getId(), persona.getPnombre(),
                                nuevaLatitud, nuevaLongitud));
                            Logger.info("Coordenadas actualizadas para: {}", persona.getPnombre());
                        } else {
                            Logger.info("Las coordenadas no han cambiado para: {}", persona.getPnombre());
                        }
                    }
                }
            } else {
                Logger.warn("No se encontraron personas en la base de datos.");
            }
        } catch (Exception e) {
            Logger.error("Error en la tarea programada: {}", e.getMessage(), e);
        }
    }
}
