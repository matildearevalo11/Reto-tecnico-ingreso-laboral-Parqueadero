package com.api.correos.services;
import com.api.correos.dtos.res.IndicadoresParqueaderoResponseDTO;
import com.api.correos.repositories.ParqueaderoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParqueaderoService {

    private final ParqueaderoRepository parqueaderoRepository;

    public List<IndicadoresParqueaderoResponseDTO> ingresosParqueaderoGeneral(){
        return parqueaderoRepository.findTop5ParqueaderosMasIngresos();
    }
    public List<IndicadoresParqueaderoResponseDTO> ingresosParqueaderoPorMes(int mes, int anio){
        YearMonth yearMonth = YearMonth.of(anio, mes);
        LocalDateTime fechaInicio = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return parqueaderoRepository.findTop5ParqueaderosMasIngresosPorFecha(fechaInicio, fechaFin);
    }

    public List<IndicadoresParqueaderoResponseDTO> ingresosParqueaderoPorAnio(int anio){
        Year year = Year.of(anio);
        LocalDateTime fechaInicio = year.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = year.atMonth(12).atEndOfMonth().atTime(23,59,59);

        return parqueaderoRepository.findTop5ParqueaderosMasIngresosPorFecha(fechaInicio, fechaFin);
    }

    public List<IndicadoresParqueaderoResponseDTO> ingresosParqueaderoPorRangoDeTiempo(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        return parqueaderoRepository.findTop5ParqueaderosMasIngresosPorFecha(fechaInicio, fechaFin);
    }
}
