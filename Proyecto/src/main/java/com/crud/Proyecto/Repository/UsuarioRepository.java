package com.crud.Proyecto.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.Proyecto.Entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByPersonaId(Long personaId);

	Optional<Usuario> findByLogin(String login);
}
