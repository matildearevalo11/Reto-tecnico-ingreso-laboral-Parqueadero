package com.api.correos.controllers;

import com.api.correos.dtos.req.FechaRequestDTO;
import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.dtos.res.IngresosMesResponseDTO;
import com.api.correos.services.CorreoService;
import com.api.correos.services.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/indicators")
@RequiredArgsConstructor
public class IndicadoresController {

    private final VehiculoService vehiculoService;
    @GetMapping("/entry-for-month")
    public int obtenerTop5IngresosVehiculosPorMes(@RequestBody FechaRequestDTO fechaRequestDTO) {
        System.out.println("fecha: "+fechaRequestDTO);
        return vehiculoService.contadorGeneralAnio(fechaRequestDTO);
    }

    @GetMapping("/conteo-entradas-anio/{anio}")
    public List<IngresosMesResponseDTO> contarEntradasPorMes(@PathVariable int anio) {
        return vehiculoService.contarEntradasPorMes(anio);
    }

    @GetMapping("/top5-ingreso-vehiculos-mes")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosMes(@RequestParam int mes, @RequestParam int anio) {
        return vehiculoService.ingresosVehiculoPorMes(mes, anio);
    }

    @GetMapping("/top5-ingreso-vehiculos-anio")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosAnio(@RequestParam int anio) {
        return vehiculoService.ingresosVehiculoPorAnio(anio);
    }
}
