package com.crud.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crud.Proyecto.Entities.Coordenadas;
import com.crud.Proyecto.Entities.Persona;
import com.crud.Proyecto.Services.PersonaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/personas")

public class PersonaController {

    @Autowired
    private PersonaService personaService;
	@Operation(summary = "Se Obtiene las coordenadas." , tags = {"Persona"})
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Encontramos las coordenadas", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Coordenadas.class)) }),
	  @ApiResponse(responseCode = "400", description = "La ID proporcionada no es valida", 
	    content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Coordenadas no encontradas", 
	    content = @Content) })
    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.crearPersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    // Metodos de busqueda
    
    @GetMapping
    public List<Persona> obtenerPersonas() {
        return personaService.obtenerPersonas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id) {
        return personaService.obtenerPersonaPorId(id)
                .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/buscar/identificacion/{identificacion}")
    public ResponseEntity<Persona> buscarPorIdentificacion(@PathVariable int identificacion) {
        return personaService.buscarPorIdentificacion(identificacion)
                .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/ubicacion")
    public ResponseEntity<String> getUbicacion(@PathVariable Long id) {
        String ubicacion = personaService.getUbicacion(id);
        if (ubicacion != null) {
            return ResponseEntity.ok(ubicacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/buscar/edad/{edad}")
    public ResponseEntity<List<Persona>> buscarPorEdad(@PathVariable int edad) {
        List<Persona> personas = personaService.buscarPorEdad(edad);
        if (personas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @GetMapping("/buscar/apellido/{papellido}")
    public ResponseEntity<List<Persona>> buscarPorPrimerApellido(@PathVariable String papellido) {
        List<Persona> personas = personaService.buscarPorPrimerApellido(papellido);
        if (personas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @GetMapping("/buscar/nombre/{pnombre}")
    public ResponseEntity<List<Persona>> buscarPorPrimerNombre(@PathVariable String pnombre) {
        List<Persona> personas = personaService.buscarPorPrimerNombre(pnombre);
        if (personas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }
    // Actualizar un usuario

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        try {
            Persona persona = personaService.actualizarPersona(id, personaActualizada);
            return new ResponseEntity<>(persona, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<Void> updateUbicacion(@PathVariable Long id, @RequestParam String nuevaUbicacion) {
        boolean actualizado = personaService.updateUbicacion(id, nuevaUbicacion);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
