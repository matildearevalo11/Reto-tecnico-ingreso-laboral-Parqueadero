package com.api.correos.controllers;
import com.api.correos.dtos.res.IndicadoresParqueaderoResponseDTO;
import com.api.correos.services.ParqueaderoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parkings")
@RequiredArgsConstructor
public class IndicadoresParqueaderoController {

    private final ParqueaderoService parqueaderoService;

    @GetMapping("/top5")
    public List<IndicadoresParqueaderoResponseDTO> obtenerTop5IngresoParqueaderoGeneral() {
        return parqueaderoService.ingresosParqueaderoGeneral();
    }
    @GetMapping("/top5/month")
    public List<IndicadoresParqueaderoResponseDTO> obtenerTop5IngresoParqueaderoMes(@RequestParam int mes, @RequestParam int anio) {
        return parqueaderoService.ingresosParqueaderoPorMes(mes, anio);
    }

    @GetMapping("/top5/year")
    public List<IndicadoresParqueaderoResponseDTO> obtenerTop5IngresoParqueaderoAnio(@RequestParam int anio) {
        return parqueaderoService.ingresosParqueaderoPorAnio(anio);
    }

    @GetMapping("/top5/date-range")
    public List<IndicadoresParqueaderoResponseDTO> obtenerTop5IngresoParqueaderoRangoDeTiempo(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        return parqueaderoService.ingresosParqueaderoPorRangoDeTiempo(fechaInicio, fechaFin);
    }
}
