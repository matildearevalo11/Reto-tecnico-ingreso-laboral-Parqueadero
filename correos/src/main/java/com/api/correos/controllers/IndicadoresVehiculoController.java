package com.api.correos.controllers;

import com.api.correos.dtos.req.FechaRequestDTO;
import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.dtos.res.IngresosMesResponseDTO;
import com.api.correos.services.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class IndicadoresVehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping("/top5")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosMes() {
        return vehiculoService.ingresosVehiculosGeneral();
    }

    @GetMapping("/top5/month")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosMes(@RequestParam int mes, @RequestParam int anio) {
        return vehiculoService.ingresosVehiculoPorMes(mes, anio);
    }

    @GetMapping("/top5/anio")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosAnio(@RequestParam int anio) {
        return vehiculoService.ingresosVehiculoPorAnio(anio);
    }

    @GetMapping("/top5/date-range")
    public List<IndicadoresVehiculoResponseDTO> obtenerTop5IngresoVehiculosRango(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        return vehiculoService.ingresosVehiculoPorRangoDeTiempo(fechaInicio, fechaFin);
    }
}
