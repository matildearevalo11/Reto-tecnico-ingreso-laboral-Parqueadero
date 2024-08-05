package com.prueba.pruebaparqueadero.services;
import com.prueba.pruebaparqueadero.entities.Rol;
import com.prueba.pruebaparqueadero.entities.Usuario;
import com.prueba.pruebaparqueadero.exceptions.ConflictException;
import com.prueba.pruebaparqueadero.repositories.ParqueaderoRepository;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParqueaderoRepository parqueaderoRepository;

    public Usuario crearSocio(Usuario socio) {
        if (usuarioRepository.existsByEmail(socio.getEmail())) {
            throw new ConflictException("El email ya está registrado");
        }
        socio.setRol(Rol.SOCIO);
        return usuarioRepository.save(socio);
    }
}