package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculosParqueadosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping("/todos-los-vehiculos-actuales/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public Page<VehiculosParqueadosDTO> obtenerVehiculosPorParqueadero(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerVehiculosPorParqueadero(idParqueadero, pageable);
    }

    @GetMapping("/detalle-vehiculos-actuales/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<VehiculoDTO> obtenerDetalleVehiculosPorParqueadero(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerDetalleVehiculosPorParqueadero(idParqueadero, pageable);
    }
}

