package com.crud.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crud.Proyecto.Entities.Coordenadas;
import com.crud.Proyecto.Services.CoordenadasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/Coordenadas")
public class CoordenadasController {
	@Autowired
	private CoordenadasService coordenadaService;
	@Operation(summary = "Se Obtiene las coordenadas." , tags = {"Coordenadas"})
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Encontramos las coordenadas", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Coordenadas.class)) }),
	  @ApiResponse(responseCode = "400", description = "La ID proporcionada no es valida", 
	    content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Coordenadas no encontradas", 
	    content = @Content) })
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/coordenadas")
	public List<Coordenadas> consultarAllCoordenadas(Pageable pageable) {
	    return coordenadaService.consutlarAllCoordenadas(pageable);
	}


}
