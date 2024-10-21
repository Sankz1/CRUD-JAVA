package com.crud.Proyecto.Services.Interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.crud.Proyecto.Entities.Coordenadas;

public interface ICoordenadasService {
 List<Coordenadas> consutlarAllCoordenadas(Pageable pageable);
}
