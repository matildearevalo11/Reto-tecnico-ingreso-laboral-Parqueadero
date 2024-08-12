package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import com.prueba.pruebaparqueadero.exceptions.ConflictException;
import com.prueba.pruebaparqueadero.exceptions.NotFoundException;
import com.prueba.pruebaparqueadero.feignclients.CorreoFeignClients;
import com.prueba.pruebaparqueadero.feignclients.dto.CorreoRequestDTO;
import com.prueba.pruebaparqueadero.repositories.HistorialVehiculosRepository;
import com.prueba.pruebaparqueadero.repositories.ParqueaderoRepository;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.dtos.req.VehiculoRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.*;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final HistorialVehiculosService historialVehiculosService;
    private final ParqueaderoService parqueaderoService;
    private final ModelMapper modelMapper;
    private final CorreoFeignClients correoFeignClients;
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);


    @Transactional
    public int registrarEntradaVehiculo(VehiculoRequestDTO vehiculoDTO, int idUsuario) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDTO.getPlaca());
        if (vehiculoOptional.isPresent()) {
            Vehiculo vehiculo = vehiculoOptional.get();
            Optional<HistorialVehiculos> historialActualOptional = historialVehiculosService.findByVehiculoAndSalidaIsNull(vehiculo);
            if (historialActualOptional.isPresent()) {
                throw new ConflictException("No se puede registrar ingreso, ya existe una entrada no salida para este vehículo");
            }
        }

        Parqueadero parqueadero = parqueaderoService.findById(vehiculoDTO.getIdParqueadero())
                .orElseThrow(() -> new NotFoundException("Parqueadero no encontrado"));

        if(parqueadero.getSocio().getId() != idUsuario){
            throw new ConflictException("Este socio no puede añadir entradas a parqueaderos que no tiene a cargo.");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(vehiculoDTO.getPlaca());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setParqueadero(parqueadero);
        vehiculo = vehiculoRepository.save(vehiculo);

        HistorialVehiculos historial = new HistorialVehiculos();
        historial.setVehiculo(vehiculo);
        historial.setParqueadero(parqueadero);
        historial.setEntrada(LocalDateTime.now());
        historial.setSalida(null);
        historial = historialVehiculosService.save(historial);

        try {
            CorreoRequestDTO correoRequestDTO = guardarCorreo(vehiculo, parqueadero, historial.getEntrada());
            correoFeignClients.enviarCorreo(correoRequestDTO);
        } catch (FeignException e) {
            logger.error("El servicio de correo no está disponible debido a lo siguiente: ".concat(e.getMessage()));
        }

        return historial.getId();
    }

    private CorreoRequestDTO guardarCorreo(Vehiculo vehiculo, Parqueadero parqueadero, LocalDateTime entrada) {
        CorreoRequestDTO correo = new CorreoRequestDTO();
        correo.setEmail(parqueadero.getSocio().getEmail());
        correo.setPlaca(vehiculo.getPlaca());
        correo.setMensaje("Correo Enviado");
        correo.setNombre(parqueadero.getNombre());
        correo.setEntrada(entrada);
        return correo;
    }

    @Transactional
    public String registrarSalidaVehiculo(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new NotFoundException("No se puede Registrar Salida, no existe la placa en el parqueadero"));

        Optional<HistorialVehiculos> historialOptional = historialVehiculosService.findByVehiculoAndSalidaIsNull(vehiculo);
        if (!historialOptional.isPresent()) {
            throw new NotFoundException("No se encontró un registro de entrada activo para el vehículo con placa " + placa);
        }

        HistorialVehiculos historial = historialOptional.get();
        historial.setSalida(LocalDateTime.now());
        historialVehiculosService.save(historial);
        return "Salida registrada";
    }
    public Page<VehiculoResponseDTO> buscarVehiculosPorPlaca(String placaCoincidencia, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculoRepository.findByPlacaCoincidencia(placaCoincidencia, pageable);
        List<VehiculoResponseDTO> listVehiculos = vehiculos.getContent().stream()
                    .map(vehicles -> modelMapper.map(vehicles, VehiculoResponseDTO.class)).toList();

        return new PageImpl<>(listVehiculos, pageable, vehiculos.getTotalElements());
    }

    public Page<VehiculoResponseDTO> obtenerDetalleVehiculosPorParqueadero(int idParqueadero, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculoRepository.findByPorParqueadero(idParqueadero, pageable);
        List<VehiculoResponseDTO> listVehiculo = vehiculos.getContent().stream()
                .map(vehicles ->  modelMapper.map(vehicles, VehiculoResponseDTO.class)).toList();
        return new PageImpl<>(listVehiculo, pageable, vehiculos.getTotalElements());
    }




    public Page<VehiculosParqueaderoResponseDTO> obtenerVehiculosPorParqueadero(int idParqueadero, Pageable pageable) {
        Page<HistorialVehiculos> vehiculos = historialVehiculosService.findByPorParqueadero(idParqueadero, pageable);
        List<VehiculosParqueaderoResponseDTO> listVehiculos = vehiculos.getContent().stream()
                .map(vehicles ->  modelMapper.map(vehicles, VehiculosParqueaderoResponseDTO.class)).toList();

        return new PageImpl<>(listVehiculos, pageable, vehiculos.getTotalElements());
    }

    public List<IngresoVehiculosResponseDTO> obtenerVehiculosMasRegistrados() {
        List<Object[]> resultados = historialVehiculosService.findVehiculosMasRegistrados();

        return resultados.stream()
                .limit(10)
                .map(result -> {
                    IngresoVehiculosResponseDTO ingreso = new IngresoVehiculosResponseDTO();
                    ingreso.setPlaca((String) result[0]);
                    ingreso.setCantidadIngresos((Long) result[1]);
                    return ingreso;
                }).toList();
    }

    public List<IngresoVehiculosResponseDTO> obtenerVehiculosMasRegistradosPorParqueadero(int idParqueadero) {
        List<Object[]> resultados = historialVehiculosService.findVehiculosMasRegistradosPorParqueadero(idParqueadero);
        return resultados.stream()
                .limit(10)
                .map(result -> {
                    IngresoVehiculosResponseDTO ingreso = new IngresoVehiculosResponseDTO();
                    ingreso.setPlaca((String) result[0]);
                    ingreso.setCantidadIngresos((Long) result[1]);
                    return ingreso;
                }).toList();
    }

    @Transactional
    public Page<VehiculosParqueaderoResponseDTO> obtenerVehiculosPrimerIngreso(int idParqueadero, Pageable pageable) {
        Page<VehiculosParqueaderoResponseDTO> vehiculosActuales = obtenerVehiculosPorParqueadero(idParqueadero, pageable);
        if (vehiculosActuales.isEmpty()) {
            logger.error("No hay vehículos en el parqueadero con ID: {}", idParqueadero);
            return vehiculosActuales;
        }

        List<VehiculosParqueaderoResponseDTO> vehiculosNuevos = vehiculosActuales.stream()
                .filter(v -> {
                    long count = historialVehiculosService.countEntriesByPlacaAndParqueadero(v.getVehiculo().getPlaca(), idParqueadero);
                    return count == 1;
                }).toList();

        return new PageImpl<>(vehiculosNuevos, pageable, vehiculosActuales.getTotalElements());
    }
}