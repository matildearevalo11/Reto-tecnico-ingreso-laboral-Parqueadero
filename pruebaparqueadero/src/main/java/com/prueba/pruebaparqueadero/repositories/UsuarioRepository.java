package com.prueba.pruebaparqueadero.repositories;

import com.prueba.pruebaparqueadero.entities.Rol;
import com.prueba.pruebaparqueadero.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByRol(Rol rol);

}
