package com.prueba.pruebaparqueadero.configuration;

import com.prueba.pruebaparqueadero.entities.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getPrincipal();
    }
}
