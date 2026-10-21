
package mx.edu.utez.proyecto4C.service.usuario;

import jakarta.transaction.Transactional;

import mx.edu.utez.proyecto4C.controller.usuario.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto4C.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto4C.model.persona.Persona;
import mx.edu.utez.proyecto4C.model.usuario.Roles;
import mx.edu.utez.proyecto4C.model.usuario.Usuario;
import mx.edu.utez.proyecto4C.repository.Persona.PersonaRepository;
import mx.edu.utez.proyecto4C.repository.usuario.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    // Constructor
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PersonaRepository personaRepository) {

        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    // Crear un usuario y su persona
    @Transactional
    public Usuario createUser(RequestCrearUsuarioDTO payload) {

        // Verificar si el username ya existe
        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new BadRequestException(
                    "Este usuario ya está registrado"
            );
        }

        // Crear la persona
        Persona newPersona = new Persona();

        newPersona.setNombres(payload.getNombres());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setFechaNacimiento(payload.getFechaNacimiento());
        newPersona.setCorreo(payload.getCorreo());
        newPersona.setCurp(payload.getCurp());

        // Guardar la persona para generar su ID
        newPersona = personaRepository.save(newPersona);

        // Crear el usuario
        Usuario newUser = new Usuario();

        newUser.setUsername(payload.getUsername());
        newUser.setPassword(payload.getPassword());
        newUser.setDescripcion(payload.getDescripcion());
        newUser.setEnable(true);
        newUser.setRol(Roles.valueOf(payload.getRol()));

        // Relacionar el usuario con la persona
        newUser.setPersona(newPersona);

        // Guardar el usuario y devolverlo
        return usuarioRepository.save(newUser);
    }
}
