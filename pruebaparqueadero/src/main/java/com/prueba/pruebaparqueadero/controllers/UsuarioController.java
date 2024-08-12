package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.configuration.SecurityUtils;
import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.UsuarioService;
import com.prueba.pruebaparqueadero.services.dtos.res.ParqueaderoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/partner/parkings")
    @PreAuthorize("hasAuthority('SOCIO')")
    public Page<ParqueaderoResponseDTO> obtenerParqueaderosDeUsuario(Pageable pageable) {
        int idUsuario = SecurityUtils.obtenerUsuarioActual().getId();
        return usuarioService.obtenerParqueaderosPorUsuarioSocio(idUsuario, pageable);
    }
}
