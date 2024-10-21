package com.crud.Proyecto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crud.Proyecto.Entities.Usuario;
import com.crud.Proyecto.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(username);

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Usuario u = usuario.get();
        return new User(u.getLogin(), u.getPassword(), new ArrayList<>());
    }
}
