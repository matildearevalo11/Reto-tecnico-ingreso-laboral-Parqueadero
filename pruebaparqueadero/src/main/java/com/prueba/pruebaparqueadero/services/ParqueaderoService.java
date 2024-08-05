package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.exceptions.ConflictException;
import com.prueba.pruebaparqueadero.exceptions.NotFoundException;
import com.prueba.pruebaparqueadero.feignclients.CorreoFeignClients;
import com.prueba.pruebaparqueadero.feignclients.dto.CorreoRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.ParqueaderoDTO;
import com.prueba.pruebaparqueadero.services.dtos.VehiculosParqueadosDTO;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.entities.Usuario;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import com.prueba.pruebaparqueadero.repositories.HistorialVehiculosRepository;
import com.prueba.pruebaparqueadero.repositories.ParqueaderoRepository;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.dtos.VehiculoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParqueaderoService {

    private final ParqueaderoRepository parqueaderoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final HistorialVehiculosRepository historialVehiculosRepository;
    private final CorreoFeignClients correoFeignClients;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ParqueaderoService.class);


    public Parqueadero crearParqueadero(Parqueadero parqueadero, int idSocio) {
        Optional<Usuario> socioOptional = usuarioRepository.findById(idSocio);
        if (socioOptional.isPresent()) {
            Usuario socio = socioOptional.get();
            parqueadero.setSocio(socio);
            return parqueaderoRepository.save(parqueadero);
        } else {
            throw new NotFoundException("No se encontró el socio identificado con: " + idSocio);
        }
    }

    public ParqueaderoDTO obtenerParqueadero(int id) {
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parqueadero con el id " + id + " no encontrado"));
        return modelMapper.map(parqueadero, ParqueaderoDTO.class);
    }

    public Parqueadero actualizarParqueadero(int id, Parqueadero parqueadero) {
        Optional<Parqueadero> parqueaderoOptional = parqueaderoRepository.findById(id);
        if (parqueaderoOptional.isPresent()) {
            Parqueadero parqueaderoExistente = parqueaderoOptional.get();
            parqueaderoExistente.setNombre(parqueadero.getNombre());
            parqueaderoExistente.setDireccion(parqueadero.getDireccion());
            parqueaderoExistente.setCapacidadVehicular(parqueadero.getCapacidadVehicular());
            parqueaderoExistente.setCostoHora(parqueadero.getCostoHora());

            return parqueaderoRepository.save(parqueaderoExistente);
        }
        return null;
    }

    public void eliminarParqueadero(int id) {
        parqueaderoRepository.deleteById(id);
    }

    @Transactional
    public int registrarEntradaVehiculo(VehiculoDTO vehiculoDTO, String email) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDTO.getPlaca());
        if (vehiculoOptional.isPresent()) {
            Vehiculo vehiculo = vehiculoOptional.get();
            Optional<HistorialVehiculos> historialActualOptional = historialVehiculosRepository.findByVehiculoAndSalidaIsNull(vehiculo);
            if (historialActualOptional.isPresent()) {
                throw new ConflictException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
            }
        }

        Optional<Parqueadero> parqueaderoOptional = parqueaderoRepository.findById(vehiculoDTO.getIdParqueadero());
        if (!parqueaderoOptional.isPresent()) {
            throw new NotFoundException("Parqueadero no encontrado");
        }
        Parqueadero parqueadero = parqueaderoOptional.get();

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
        historial = historialVehiculosRepository.save(historial);

        try {
            CorreoRequestDTO correoRequestDTO = guardarCorreo(vehiculo, parqueadero);
            correoFeignClients.enviarCorreo(correoRequestDTO);
        } catch (FeignException e) {
            logger.error("El servicio de correo no está disponible debido a lo siguiente: ".concat(e.getMessage()));
        }

        return historial.getId();
    }

    private CorreoRequestDTO guardarCorreo(Vehiculo vehiculo, Parqueadero parqueadero) {
        CorreoRequestDTO correo = new CorreoRequestDTO();
        correo.setEmail(parqueadero.getSocio().getEmail());
        correo.setPlaca(vehiculo.getPlaca());
        correo.setMensaje("Correo Enviado");
        correo.setIdParqueadero(parqueadero.getId());
        return correo;
    }

    @Transactional
    public void registrarSalidaVehiculo(String placa) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(placa);
        if (!vehiculoOptional.isPresent()) {
            throw new NotFoundException("No se puede Registrar Salida, no existe la placa en el parqueadero");
        }
        Vehiculo vehiculo = vehiculoOptional.get();

        Optional<HistorialVehiculos> historialOptional = historialVehiculosRepository.findByVehiculoAndSalidaIsNull(vehiculo);
        if (!historialOptional.isPresent()) {
            throw new NotFoundException("No se encontró un registro de entrada activo para el vehículo con placa " + placa);
        }

        HistorialVehiculos historial = historialOptional.get();
        historial.setSalida(LocalDateTime.now());
        historialVehiculosRepository.save(historial);
    }

    private BigDecimal calcularCostoParqueadero(LocalDateTime entrada, LocalDateTime salida, BigDecimal costoHora) {
        if (salida == null) {
            throw new ConflictException("El vehículo aún sigue en el parqueadero");
        }

        long minutos = java.time.Duration.between(entrada, salida).toMinutes();
        BigDecimal horas = BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), RoundingMode.CEILING);
        return horas.multiply(costoHora).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal calcularGananciasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin, int idParqueadero) {
        List<HistorialVehiculos> historiales = historialVehiculosRepository.findByEntradaBetween(idParqueadero, fechaInicio, fechaFin);

        BigDecimal totalGanancias = BigDecimal.ZERO;

        for (HistorialVehiculos historial : historiales) {
            if (historial.getSalida() != null) {
                BigDecimal valor = calcularCostoParqueadero(historial.getEntrada(), historial.getSalida(), historial.getParqueadero().getCostoHora());
                totalGanancias = totalGanancias.add(valor);
            }
        }
        return totalGanancias;
    }

    public BigDecimal calcularGananciasDelDia(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NotFoundException("Parqueadero no encontrado"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inicioDia = now.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);

        return calcularGananciasPorPeriodo(inicioDia, finDia, idParqueadero);
    }

    public BigDecimal calcularGananciasDelMes(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ConflictException("Parqueadero no encontrado"));

        LocalDate now = LocalDate.now();
        LocalDate inicioMes = now.withDayOfMonth(1);
        LocalDate finMes = now.plusMonths(1).withDayOfMonth(1);

        return calcularGananciasPorPeriodo(inicioMes.atStartOfDay(), finMes.atStartOfDay(), idParqueadero);
    }

    public BigDecimal calcularGananciasDelAnio(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ConflictException("Parqueadero no encontrado"));
        LocalDate now = LocalDate.now();
        LocalDate inicioAnio = now.withDayOfYear(1);
        LocalDate finAnio = now.plusYears(1).withDayOfYear(1);

        return calcularGananciasPorPeriodo(inicioAnio.atStartOfDay(), finAnio.atStartOfDay(), idParqueadero);
    }

    public Page<ParqueaderoDTO> obtenerParqueaderosPorUsuarioSocio(int idUsuario, Pageable pageable) {
        Page<Parqueadero> parqueaderos = parqueaderoRepository.findBySocio_Id(idUsuario, pageable);
        List<ParqueaderoDTO> listParqueadero = parqueaderos.getContent().stream().map(p -> modelMapper.map(p, ParqueaderoDTO.class)).collect(Collectors.toList());
        Page<ParqueaderoDTO> parqueaderoDTOPage = new PageImpl<>(listParqueadero, pageable, parqueaderos.getTotalElements());
        return parqueaderoDTOPage;
    }

    public Page<ParqueaderoDTO> obtenerParqueaderosExistentes(Pageable pageable) {
        Page<Parqueadero> parqueaderos = parqueaderoRepository.findAll(pageable);
        List<ParqueaderoDTO> listParqueadero = parqueaderos.getContent().stream().map(p -> modelMapper.map(p, ParqueaderoDTO.class)).collect(Collectors.toList());
        Page<ParqueaderoDTO> parqueaderoDTOPage = new PageImpl<>(listParqueadero, pageable, parqueaderos.getTotalElements());
        return parqueaderoDTOPage;

    }

}






