package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.entities.Vehiculo;
import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculosParqueadosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indicadores")
@RequiredArgsConstructor
public class IndicadoresController {

    private final ParqueaderoService parqueaderoService;
    private final VehiculoService vehiculoService;

    @GetMapping("/10-vehiculos-mas-registrados")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public ResponseEntity<List<Map<String, Object>>> obtener10VehiculosMasRegistrados() {
        List<Map<String, Object>> vehiculos = vehiculoService.obtenerVehiculosMasRegistrados();
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/10-vehiculos-mas-registrados-parqueadero/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public ResponseEntity<List<Map<String, Object>>> obtener10VehiculosMasRegistradosPorParqueadero(@PathVariable int idParqueadero) {
        List<Map<String, Object>> vehiculos = vehiculoService.obtenerVehiculosMasRegistradosPorParqueadero(idParqueadero);
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/vehiculos-primer-ingreso/{idParqueadero}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public Page<VehiculosParqueadosDTO> obtenerVehiculosPrimerIngreso(@PathVariable int idParqueadero, Pageable pageable) {
        return vehiculoService.obtenerVehiculosPrimerIngreso(idParqueadero, pageable);
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

    @GetMapping("/buscar-placa-por-coincidencia")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public Page<VehiculoDTO> buscarVehiculosPorPlaca(@RequestParam String placaCoincidencia, Pageable pageable) {
        return vehiculoService.buscarVehiculosPorPlaca(placaCoincidencia, pageable);
    }
}
