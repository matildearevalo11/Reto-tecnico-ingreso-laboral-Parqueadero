package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.configuration.SecurityUtils;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import com.prueba.pruebaparqueadero.services.dtos.ParqueaderoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parqueadero")
@RequiredArgsConstructor
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;
    private final VehiculoService vehiculoService;

    @PostMapping(value = "/crear-parqueadero")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Parqueadero> crearParqueadero(@RequestBody ParqueaderoDTO parqueaderoDto) {
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.setNombre(parqueaderoDto.getNombre());
        parqueadero.setDireccion(parqueaderoDto.getDireccion());
        parqueadero.setCapacidadVehicular(parqueaderoDto.getCapacidadVehicular());
        parqueadero.setCostoHora(parqueaderoDto.getCostoHora());

        int socio = parqueaderoDto.getSocio();

        Parqueadero parqueaderoCreado = parqueaderoService.crearParqueadero(parqueadero, socio);
        return ResponseEntity.ok(parqueaderoCreado);
    }

    @GetMapping("/obtener-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Parqueadero obtenerParqueadero(@PathVariable int id) {
        return parqueaderoService.obtenerParqueadero(id);
    }

    @PutMapping("/actualizar-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Parqueadero actualizarParqueadero(@PathVariable int id, @RequestBody Parqueadero parqueadero) {
        return parqueaderoService.actualizarParqueadero(id, parqueadero);
    }

    @DeleteMapping("/eliminar-parqueadero/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminarParqueadero(@PathVariable int id) {
        parqueaderoService.eliminarParqueadero(id);
    }

    @GetMapping("/todos-mis-parqueaderos")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<List<Parqueadero>> obtenerParqueaderosDeUsuario() {
        int idUsuario = SecurityUtils.obtenerUsuarioActual().getId();
        List<Parqueadero> parqueaderos = parqueaderoService.obtenerParqueaderosPorUsuarioSocio(idUsuario);
        return ResponseEntity.ok(parqueaderos);
    }

    @PostMapping("/registre-ingreso")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<?> registrarEntradaVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        try {
            String email = SecurityUtils.obtenerUsuarioActual().getEmail();
            int idRegistro = parqueaderoService.registrarEntradaVehiculo(vehiculoDTO, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("id", idRegistro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero"));
        }
    }

    @PostMapping("/registrar-salida")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, String>> registrarSalidaVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        try {
            parqueaderoService.registrarSalidaVehiculo(vehiculoDTO.getPlaca());
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("mensaje", "Salida registrada"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se puede Registrar Salida, no existe la placa en el parqueadero"));
        }
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




