package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.res.IngresoVehiculosResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.VehiculoResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.VehiculosParqueaderoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/indicators")
@RequiredArgsConstructor
public class IndicadoresController {

    private final ParqueaderoService parqueaderoService;
    private final VehiculoService vehiculoService;

    @GetMapping("/10-most-registered")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public List<IngresoVehiculosResponseDTO> obtener10VehiculosMasRegistrados() {
        return vehiculoService.obtenerVehiculosMasRegistrados();
    }

    @GetMapping("/10-most-registered-by-parking/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public List<IngresoVehiculosResponseDTO> obtener10VehiculosMasRegistradosPorParqueadero(@PathVariable int idParqueadero) {
        return vehiculoService.obtenerVehiculosMasRegistradosPorParqueadero(idParqueadero);
    }

    @GetMapping("/first-time/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public Page<VehiculosParqueaderoResponseDTO> obtenerVehiculosPrimerIngreso(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerVehiculosPrimerIngreso(idParqueadero, pageable);
    }

    @GetMapping("/earnings-for-day/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public BigDecimal obtenerGananciasDelDia(@PathVariable int idParqueadero) {
        return parqueaderoService.calcularGananciasDelDia(idParqueadero);
    }

    @GetMapping("/earnings-for-month/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public BigDecimal obtenerGananciasDelMes(@PathVariable int idParqueadero) {
        return parqueaderoService.calcularGananciasDelMes(idParqueadero);
    }

    @GetMapping("/earnings-for-year/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public BigDecimal obtenerGananciasDelAnio(@PathVariable int idParqueadero) {
        return parqueaderoService.calcularGananciasDelAnio(idParqueadero);
    }

    @GetMapping("/vehicles-by-word")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public Page<VehiculoResponseDTO> buscarVehiculosPorPlaca(@RequestParam String placaCoincidencia, Pageable pageable) {
        return vehiculoService.buscarVehiculosPorPlaca(placaCoincidencia, pageable);
    }
}

