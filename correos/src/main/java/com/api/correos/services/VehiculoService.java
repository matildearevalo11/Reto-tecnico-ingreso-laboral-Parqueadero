package com.api.correos.services;
import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.dtos.res.IngresosMesResponseDTO;
import com.api.correos.repositories.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public List<IndicadoresVehiculoResponseDTO> ingresosVehiculosGeneral(){
        return vehiculoRepository.findTop5VehiculosMasIngresosGeneral();
    }

    public List<IndicadoresVehiculoResponseDTO> ingresosVehiculoPorMes(int mes, int anio){
        YearMonth yearMonth = YearMonth.of(anio, mes);
        LocalDateTime fechaInicio = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return vehiculoRepository.findTop5VehiculosMasIngresos(fechaInicio, fechaFin);
    }

    public List<IndicadoresVehiculoResponseDTO> ingresosVehiculoPorAnio(int anio){
        Year year = Year.of(anio);
        LocalDateTime fechaInicio = year.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = year.atMonth(12).atEndOfMonth().atTime(23,59,59);

        return vehiculoRepository.findTop5VehiculosMasIngresos(fechaInicio, fechaFin);
    }

    public List<IndicadoresVehiculoResponseDTO> ingresosVehiculoPorRangoDeTiempo(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        return vehiculoRepository.findTop5VehiculosMasIngresos(fechaInicio, fechaFin);
    }




















    public int contadorGeneralMes(int mes, int anio){
        YearMonth yearMonth = YearMonth.of(anio, mes);
        LocalDateTime fechaInicio = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return vehiculoRepository.countByEntradaBetween(fechaInicio, fechaFin);
    }

    public int contadorGeneralAnio(int anio){
        Year year = Year.of(anio);
        LocalDateTime fechaInicio = year.atDay(1).atStartOfDay();
        LocalDateTime fechaFin = year.atMonth(12).atEndOfMonth().atTime(23,59,59);
        return vehiculoRepository.countByEntradaBetween(fechaInicio, fechaFin);
    }

    public List<IngresosMesResponseDTO> contarEntradasPorMes(int anio) {
        List<IngresosMesResponseDTO> listMeses= new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            YearMonth yearMonth = YearMonth.of(anio, mes);
            LocalDateTime fechaInicio = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime fechaFin = yearMonth.atEndOfMonth().atTime(23, 59, 59);

            int conteo = vehiculoRepository.countByEntradaBetween(fechaInicio, fechaFin);
            String nombreMes = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());

            IngresosMesResponseDTO mesResponseDTO = new IngresosMesResponseDTO();
            mesResponseDTO.setMes(nombreMes);
            mesResponseDTO.setCantidadIngresos(conteo);
            listMeses.add(mesResponseDTO);
        }

        return listMeses;
    }


}
