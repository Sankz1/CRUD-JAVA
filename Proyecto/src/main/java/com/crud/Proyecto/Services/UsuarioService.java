package com.crud.Proyecto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.Proyecto.Entities.Usuario;
import com.crud.Proyecto.Repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void cambiarPassword(Long usuarioId, String nuevoPassword) {
        usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
            usuario.setPassword(nuevoPassword);
            usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> obtenerUsuarioDetalles(Long personaId) {
        return usuarioRepository.findByPersonaId(personaId);
    }

    public Optional<Usuario> obtenerPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
