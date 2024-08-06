package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import com.prueba.pruebaparqueadero.repositories.HistorialVehiculosRepository;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculosParqueadosDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final HistorialVehiculosRepository historialRepository;
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);


    public Page<VehiculoDTO> buscarVehiculosPorPlaca(String placaCoincidencia, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculoRepository.findByPlacaCoincidencia(placaCoincidencia, pageable);
        List<VehiculoDTO> listVehiculos = vehiculos.getContent().stream()
                .map(v -> new VehiculoDTO(v.getPlaca(), v.getParqueadero().getId(), v.getMarca(),
                        v.getModelo(), v.getColor())).toList();

        return new PageImpl<>(listVehiculos, pageable, vehiculos.getTotalElements());
    }

    public Page<VehiculoDTO> obtenerDetalleVehiculosPorParqueadero(int idParqueadero, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculoRepository.findByPorParqueadero(idParqueadero, pageable);
        List<VehiculoDTO> listVehiculo = vehiculos.getContent().stream()
                .map(v -> new VehiculoDTO(v.getPlaca(), v.getParqueadero().getId(), v.getMarca(),
                        v.getModelo(), v.getColor())).toList();
        return new PageImpl<>(listVehiculo, pageable, vehiculos.getTotalElements());
    }
    public Page<VehiculosParqueadosDTO> obtenerVehiculosPorParqueadero(int idParqueadero, Pageable pageable) {
        Page<HistorialVehiculos> historiales = historialRepository.findByPorParqueadero(idParqueadero, pageable);

        List<VehiculosParqueadosDTO> vehiculos = historiales.getContent().stream()
                .map(h -> new VehiculosParqueadosDTO(h.getId(),
                        h.getVehiculo().getPlaca(),
                        h.getEntrada())).toList();
        return new PageImpl<>(vehiculos, pageable, historiales.getTotalElements());
    }

    public List<Map<String, Object>> obtenerVehiculosMasRegistrados() {
        List<Object[]> resultados = historialRepository.findVehiculosMasRegistrados();

        return resultados.stream()
                .limit(10)
                .map(result -> {
                    String placa = (String) result[0];
                    Long cantidadIngresos = (Long) result[1];
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("placa", placa);
                    mapa.put("cantidadIngresos", cantidadIngresos);
                    return mapa;
                }).toList();
    }

    public List<Map<String, Object>> obtenerVehiculosMasRegistradosPorParqueadero(int idParqueadero) {
        List<Object[]> resultados = historialRepository.findVehiculosMasRegistradosPorParqueadero(idParqueadero);

        return resultados.stream()
                .limit(10)
                .map(result -> {
                    String placa = (String) result[0];
                    Long cantidadIngresos = (Long) result[1];
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("placa", placa);
                    mapa.put("cantidadIngresos", cantidadIngresos);
                    return mapa;
                }).toList();
    }

    @Transactional
    public Page<VehiculosParqueadosDTO> obtenerVehiculosPrimerIngreso(int idParqueadero, Pageable pageable) {
        Page<VehiculosParqueadosDTO> vehiculosActuales = obtenerVehiculosPorParqueadero(idParqueadero, pageable);
        if (vehiculosActuales.isEmpty()) {
            logger.error("No hay vehículos en el parqueadero con ID: {}", idParqueadero);
            return vehiculosActuales;
        }

        List<VehiculosParqueadosDTO> vehiculosNuevos = vehiculosActuales.stream()
                .filter(v -> {
                    long count = historialRepository.countEntriesByPlacaAndParqueadero(v.getPlaca(), idParqueadero);
                    return count == 1;
                }).toList();

        return new PageImpl<>(vehiculosNuevos, pageable, vehiculosActuales.getTotalElements());
    }
}
