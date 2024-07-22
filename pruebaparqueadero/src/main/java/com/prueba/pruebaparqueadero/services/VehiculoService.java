package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import com.prueba.pruebaparqueadero.repositories.HistorialVehiculosRepository;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculosParqueadosDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final HistorialVehiculosRepository historialRepository;

    public List<Vehiculo> buscarVehiculosPorPlaca(String placaCoincidencia) {
        return vehiculoRepository.findByPlacaCoincidencia(placaCoincidencia);
    }

    public List<VehiculoDTO> obtenerDetalleVehiculosPorParqueadero(int idParqueadero) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByPorParqueadero(idParqueadero);
        return vehiculos.stream()
                .map(v -> new VehiculoDTO(v.getPlaca(), v.getParqueadero().getId(), v.getMarca(),
                        v.getModelo(), v.getColor()))
                .collect(Collectors.toList());
    }

    public List<VehiculosParqueadosDTO> obtenerVehiculosPorParqueadero(int idParqueadero) {
        List<HistorialVehiculos> historiales = historialRepository.findByPorParqueadero(idParqueadero);

        return historiales.stream()
                .map(h -> new VehiculosParqueadosDTO(h.getId(),
                        h.getVehiculo().getPlaca(),
                        h.getEntrada()))
                .collect(Collectors.toList());
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
                })
                .collect(Collectors.toList());
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
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VehiculosParqueadosDTO> obtenerVehiculosPrimerIngreso(int idParqueadero) {
        List<VehiculosParqueadosDTO> vehiculosActuales = obtenerVehiculosPorParqueadero(idParqueadero);
        if (vehiculosActuales.isEmpty()) {
            System.out.println("No hay vehículos en el parqueadero con ID: " + idParqueadero);
            return vehiculosActuales;
        }

        List<VehiculosParqueadosDTO> vehiculosNuevos = vehiculosActuales.stream()
                .filter(v -> {
                    long count = historialRepository.countEntriesByPlacaAndParqueadero(v.getPlaca(), idParqueadero);
                    return count == 1;
                })
                .collect(Collectors.toList());

        return vehiculosNuevos;
    }
}
