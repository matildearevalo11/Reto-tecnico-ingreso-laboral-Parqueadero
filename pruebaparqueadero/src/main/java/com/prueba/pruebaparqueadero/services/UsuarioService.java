package com.prueba.pruebaparqueadero.services;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.entities.Rol;
import com.prueba.pruebaparqueadero.entities.Usuario;
import com.prueba.pruebaparqueadero.exceptions.ConflictException;
import com.prueba.pruebaparqueadero.exceptions.NotFoundException;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import com.prueba.pruebaparqueadero.services.dtos.res.ParqueaderoResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParqueaderoService parqueaderoService;
    private final ModelMapper modelMapper;

    public Usuario crearSocio(Usuario socio) {
        if (usuarioRepository.existsByEmail(socio.getEmail())) {
            throw new ConflictException("El email ya está registrado");
        }
        socio.setRol(Rol.SOCIO);
        return usuarioRepository.save(socio);
    }

    public UsuarioResponseDTO getUserById(int id) {
        Usuario user= usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario con el id " + id + " no encontrado"));
        return this.modelMapper.map(user, UsuarioResponseDTO.class);
    }


    public Usuario findByUserEmail(String userEmail) {
        return this.usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("El usuario con email" + userEmail + " no encontarado"));
    }

    public Page<ParqueaderoResponseDTO> obtenerParqueaderosPorUsuarioSocio(int idUsuario, Pageable pageable) {
        Page<Parqueadero> parqueaderos = parqueaderoService.findByIdSocio(idUsuario, pageable);
        List<ParqueaderoResponseDTO> listParqueadero = parqueaderos.getContent().stream().map(p -> modelMapper.map(p, ParqueaderoResponseDTO.class)).toList();
        return new PageImpl<>(listParqueadero, pageable, parqueaderos.getTotalElements());
    }
}