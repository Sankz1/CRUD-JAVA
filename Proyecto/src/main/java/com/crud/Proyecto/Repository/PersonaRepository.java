package com.crud.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.Proyecto.Entities.Persona;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	Optional<Persona> findByIdentificacion(int identificacion);
	List<Persona> findByEdad(int edad);
	List<Persona> findByPapellido(String papellido);
	List<Persona> findByPnombre(String pnombre);
    List<Persona> findAll();

}
