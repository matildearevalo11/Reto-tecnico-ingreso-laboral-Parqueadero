package com.prueba.pruebaparqueadero.controllers;
import com.prueba.pruebaparqueadero.configuration.SecurityUtils;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.req.VehiculoRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.HistorialVehiculosResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.MensajeResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.VehiculoResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.VehiculosParqueaderoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping("/save-entry/parking")
    @PreAuthorize("hasAuthority('SOCIO')")
    @ResponseStatus(HttpStatus.CREATED)
    public int registrarEntradaVehiculo(@Valid @RequestBody VehiculoRequestDTO vehiculoDTO) {
        int idUsuario = SecurityUtils.obtenerUsuarioActual().getId();
        HistorialVehiculosResponseDTO historial = vehiculoService.registrarEntradaVehiculo(vehiculoDTO, idUsuario);
        return historial.getId();
    }

    @PostMapping("/save-output/parking")
    @PreAuthorize("hasAuthority('SOCIO')")
        public MensajeResponseDTO registrarSalidaVehiculo(@Valid @RequestBody VehiculoRequestDTO vehiculoDTO) {
        return vehiculoService.registrarSalidaVehiculo(vehiculoDTO.getPlaca());
    }
    @GetMapping("/by-parking/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public Page<VehiculosParqueaderoResponseDTO> obtenerVehiculosPorParqueadero(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerVehiculosPorParqueadero(idParqueadero, pageable);
    }

    @GetMapping("/information/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<VehiculoResponseDTO> obtenerDetalleVehiculosPorParqueadero(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerDetalleVehiculosPorParqueadero(idParqueadero, pageable);
    }
}

