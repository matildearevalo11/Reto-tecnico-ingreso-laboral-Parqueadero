package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.configuration.SecurityUtils;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.ParqueaderoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/parqueaderos")
@RequiredArgsConstructor
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;
    private final VehiculoService vehiculoService;
    private static final String MESSAGE = "message";

    @PostMapping(value = "/crear-parqueadero")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> crearParqueadero(@RequestBody ParqueaderoDTO parqueaderoDto) {
        Parqueadero parqueaderoCreado = parqueaderoService.crearParqueadero(parqueaderoDto, parqueaderoDto.getIdSocio());
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap(MESSAGE,"Ha sido creado el parqueadero "+ parqueaderoCreado.getNombre()+ ""));
    }

    @GetMapping("/obtener-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ParqueaderoDTO obtenerParqueadero(@PathVariable int id) {
        return parqueaderoService.obtenerParqueadero(id);
    }

    @PutMapping("/actualizar-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> actualizarParqueadero(@PathVariable int id, @RequestBody ParqueaderoDTO parqueadero) {
        Parqueadero parq = parqueaderoService.actualizarParqueadero(id, parqueadero);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap(MESSAGE,"Ha sido actualizado el parqueadero "+ parq.getNombre()+ ""));

    }

    @DeleteMapping("/eliminar-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarParqueadero(@PathVariable int id) {
        parqueaderoService.eliminarParqueadero(id);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap(MESSAGE, "El parqueadero ha sido eliminado exitosamente"));

    }

    @GetMapping("/todos-mis-parqueaderos")
    @PreAuthorize("hasAuthority('SOCIO')")
    public Page<ParqueaderoDTO> obtenerParqueaderosDeUsuario(Pageable pageable) {
        int idUsuario = SecurityUtils.obtenerUsuarioActual().getId();
        return parqueaderoService.obtenerParqueaderosPorUsuarioSocio(idUsuario, pageable);
    }

    @GetMapping("/todos-los-parqueaderos-existentes")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ParqueaderoDTO> obtenerParqueaderosExistentes(Pageable pageable) {
        return parqueaderoService.obtenerParqueaderosExistentes(pageable);
    }

    @PostMapping("/registre-ingreso")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, Integer>> registrarEntradaVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
            String email = SecurityUtils.obtenerUsuarioActual().getEmail();
            int idRegistro = parqueaderoService.registrarEntradaVehiculo(vehiculoDTO, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("id", idRegistro));
    }

    @PostMapping("/registrar-salida")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, String>> registrarSalidaVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
            parqueaderoService.registrarSalidaVehiculo(vehiculoDTO.getPlaca());
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap(MESSAGE, "Salida registrada"));
    }

    @GetMapping("/ganancias-dia/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<BigDecimal> obtenerGananciasDelDia(@PathVariable int idParqueadero) {
        BigDecimal ganancias = parqueaderoService.calcularGananciasDelDia(idParqueadero);
        return ResponseEntity.ok(ganancias);
    }

    @GetMapping("/ganancias-mes/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<BigDecimal> obtenerGananciasDelMes(@PathVariable int idParqueadero) {
        BigDecimal ganancias = parqueaderoService.calcularGananciasDelMes(idParqueadero);
        return ResponseEntity.ok(ganancias);
    }

    @GetMapping("/ganancias-anio/{idParqueadero}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<BigDecimal> obtenerGananciasDelAnio(@PathVariable int idParqueadero) {
        BigDecimal ganancias = parqueaderoService.calcularGananciasDelAnio(idParqueadero);
        return ResponseEntity.ok(ganancias);
    }
}




