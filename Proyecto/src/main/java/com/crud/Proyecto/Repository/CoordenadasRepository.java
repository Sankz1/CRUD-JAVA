package com.crud.Proyecto.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crud.Proyecto.Entities.Coordenadas;

import java.util.List;

@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long>, CrudRepository<Coordenadas, Long> {

    public abstract Page<Coordenadas> findAll(Pageable pageable);
    
    // Método modificado para obtener una lista de coordenadas por persona
    @Query("SELECT c FROM Coordenadas c WHERE c.persona = :id_persona AND c.latitud IS NOT NULL AND c.longitud IS NOT NULL")
    public abstract List<Coordenadas> getCoordenadaXPersona(@Param("id_persona") Long persona);
    
    // Método para obtener todas las coordenadas válidas
    @Query("SELECT c FROM Coordenadas c WHERE c.latitud IS NOT NULL AND c.longitud IS NOT NULL")
    public abstract List<Coordenadas> findAllValidCoordenadas();
}

