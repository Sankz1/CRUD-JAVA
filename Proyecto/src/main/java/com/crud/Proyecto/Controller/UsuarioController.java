package com.crud.Proyecto.Controller;

import JwtUtils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.crud.Proyecto.Entities.Usuario;
import com.crud.Proyecto.Services.MyUserDetailsService;
import com.crud.Proyecto.Services.UsuarioService;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Asegúrate de que coincida con la URL de tu frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest) {
        // Verificar la existencia de la API Key
        String apiKeyHeader = httpServletRequest.getHeader("X-ApiKey");
        if (apiKeyHeader == null || apiKeyHeader.isEmpty()) {
            return new ResponseEntity<>("Api Key Faltante", HttpStatus.UNAUTHORIZED);
        }

        // Intentar autenticar al usuario
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        // Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorLogin(request.getUsername());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar si la API Key es válida
            if (!usuario.getApikey().equals(apiKeyHeader)) {
                return new ResponseEntity<>("Api Key no es válida", HttpStatus.UNAUTHORIZED);
            }

            // Generar el token JWT y devolverlo en la respuesta
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@PathVariable Long id, @RequestBody String nuevoPassword) {
        String encodedPassword = passwordEncoder.encode(nuevoPassword);
        usuarioService.cambiarPassword(id, encodedPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detalles/{idpersona}")
    public ResponseEntity<Usuario> obtenerUsuarioDetalles(@PathVariable Long idpersona) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioDetalles(idpersona);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
