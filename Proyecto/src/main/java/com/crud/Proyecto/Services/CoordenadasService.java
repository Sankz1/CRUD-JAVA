package com.crud.Proyecto.Services;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crud.Proyecto.Entities.Coordenadas;
import com.crud.Proyecto.Repository.CoordenadasRepository;
import com.crud.Proyecto.Services.Interfaces.ICoordenadasService;

@Service 
public class CoordenadasService implements ICoordenadasService{
	@Autowired
	private CoordenadasRepository ICoordenadaRepository;
	
	private static final Logger Logger = org.apache.logging.log4j.LogManager.getLogger(PersonaService.class);

	@Override
	public List<Coordenadas> consutlarAllCoordenadas(Pageable pageable) {
		return ICoordenadaRepository.findAll(pageable).getContent();
	}
	
	

}
