package com.crud.Proyecto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.Proyecto.Entities.Persona;
import com.crud.Proyecto.Entities.Usuario;
import com.crud.Proyecto.Repository.PersonaRepository;
import com.crud.Proyecto.Repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Persona crearPersona(Persona persona) {
        // Metodo para calcular la edad y la edad clinica
        persona.calcularEdad();
        persona.calcularEdadClinica();

        // Guardar persona
        Persona personaGuardada = personaRepository.save(persona);

        // Metodo para crear el usuario asociado
        Usuario usuario = new Usuario();
        usuario.setPersona(personaGuardada);
        usuario.generarLogin();
        usuario.generarPassword();
        usuario.generarApiKey();

        String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrada);

        // Guardar usuario
        usuarioRepository.save(usuario);

        return personaGuardada;
    }
// Metodos de busqueda
    public List<Persona> obtenerPersonas() {
        return personaRepository.findAll();
    }

    public Optional<Persona> obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }
    public Optional<Persona> buscarPorIdentificacion(int identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }	
    public List<Persona> buscarPorEdad(int edad) {
        return personaRepository.findByEdad(edad);
    }
    public List<Persona> buscarPorPrimerApellido(String papellido) {
        return personaRepository.findByPapellido(papellido);
    }
    public List<Persona> buscarPorPrimerNombre(String pnombre) {
        return personaRepository.findByPnombre(pnombre);
    }

    


    @Transactional
    public Persona actualizarPersona(Long id, Persona personaActualizada) {
        return personaRepository.findById(id)
                .map(persona -> {
                    persona.setIdentificacion(personaActualizada.getIdentificacion());
                    persona.setPnombre(personaActualizada.getPnombre());
                    persona.setSnombre(personaActualizada.getSnombre());
                    persona.setPapellido(personaActualizada.getPapellido());
                    persona.setSapellido(personaActualizada.getSapellido());
                    persona.setEmail(personaActualizada.getEmail());
                    persona.setFechanacimiento(personaActualizada.getFechanacimiento());
                    persona.calcularEdad();
                    persona.calcularEdadClinica();
                    return personaRepository.save(persona);
                }).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }

    // Obtener ubicación de una persona
    public String getUbicacion(Long id) {
        return personaRepository.findById(id)
                .map(Persona::getUbicacion) // Asumiendo que tienes un método getUbicacion en la clase Persona
                .orElse(null);
    }
    // Actualizar ubicación de una persona
    public boolean updateUbicacion(Long id, String nuevaUbicacion) {
        return personaRepository.findById(id)
                .map(persona -> {
                    persona.setUbicacion(nuevaUbicacion); // Asumiendo que tienes un método setUbicacion en la clase Persona
                    personaRepository.save(persona);
                    return true;
                }).orElse(false);
    }
}
