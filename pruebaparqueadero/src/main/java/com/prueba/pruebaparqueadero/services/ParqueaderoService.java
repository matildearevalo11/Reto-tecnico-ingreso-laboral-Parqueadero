package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.entities.*;
import com.prueba.pruebaparqueadero.exceptions.ConflictException;
import com.prueba.pruebaparqueadero.exceptions.NotFoundException;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.dtos.req.ParqueaderoRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.IdResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.ParqueaderoResponseDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.prueba.pruebaparqueadero.repositories.ParqueaderoRepository;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParqueaderoService {

    private final ParqueaderoRepository parqueaderoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistorialVehiculosService historialService;
    private final VehiculoRepository vehiculoRepository;
    private final ModelMapper modelMapper;
    private static final String PARQUEADERO_NO_ENCONTRADO = "Parqueadero no encontrado";
    private static final Logger logger = LoggerFactory.getLogger(ParqueaderoService.class);


    public IdResponseDTO crearParqueadero(ParqueaderoRequestDTO parqueaderoDto, int idSocio) {
        Usuario socio = usuarioRepository.findById(idSocio)
                .orElseThrow(() -> new NotFoundException("Socio con el id " + idSocio + " no encontrado"));

        if(socio.getRol()!= Rol.ADMIN){
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.setNombre(parqueaderoDto.getNombre());
        parqueadero.setDireccion(parqueaderoDto.getDireccion());
        parqueadero.setCapacidadVehicular(parqueaderoDto.getCapacidadVehicular());
        parqueadero.setCostoHora(parqueaderoDto.getCostoHora());
        parqueadero.setSocio(modelMapper.map(socio, Usuario.class));
        parqueaderoRepository.save(parqueadero);
        return modelMapper.map(parqueadero, IdResponseDTO.class);
        }
        else {
            throw  new ConflictException("Un admin no puede tener un parqueadero asociado.");
        }
    }

    public ParqueaderoResponseDTO obtenerParqueadero(int id) {
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parqueadero con el id " + id + " no encontrado"));
        return modelMapper.map(parqueadero, ParqueaderoResponseDTO.class);
    }

    public ParqueaderoResponseDTO actualizarParqueadero(int id, ParqueaderoRequestDTO parqueadero) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Parqueadero parqueadero1 = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PARQUEADERO_NO_ENCONTRADO));


        parqueadero1.setNombre(parqueadero.getNombre());
        parqueadero1.setDireccion(parqueadero.getDireccion());
        if(parqueadero.getCapacidadVehicular() >= vehiculoRepository.findByPorParqueadero(id, pageable).getTotalElements()){
        parqueadero1.setCapacidadVehicular(parqueadero.getCapacidadVehicular());}
        else{
            throw new ConflictException("La capacidad vehicular es menor a la cantidad actual de vehiculos parqueados.");
        }
        parqueadero1.setCostoHora(parqueadero.getCostoHora());
        parqueaderoRepository.save(parqueadero1);
        return modelMapper.map(parqueadero1, ParqueaderoResponseDTO.class);
    }

    public void eliminarParqueadero(int id) {
        if (!parqueaderoRepository.existsById(id)) {
            throw new NotFoundException("Parqueadero con id " + id + " no encontrado.");
        }
        if(historialService.countByParqueaderoAndSalidaIsNull(id) > 0) {
            throw new ConflictException("No puede eliminar un parqueadero con vehiculos parqueados.");
        }
        parqueaderoRepository.deleteById(id);
    }

    public BigDecimal calcularCostoParqueadero(LocalDateTime entrada, LocalDateTime salida, BigDecimal costoHora) {
        if (salida == null) {
            throw new ConflictException("El vehículo aún sigue en el parqueadero");
        }

        if (entrada == null || costoHora == null) {
            throw new IllegalArgumentException("La entrada, salida o costo por hora no pueden ser null");
        }
        long minutos = java.time.Duration.between(entrada, salida).toMinutes();
        BigDecimal horas = BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), RoundingMode.CEILING);
        return horas.multiply(costoHora).setScale(2, RoundingMode.HALF_UP);
    }




    public BigDecimal calcularGananciasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin, int idParqueadero) {
        parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NotFoundException(PARQUEADERO_NO_ENCONTRADO));

        List<HistorialVehiculos> historiales = historialService.findByEntradaBetween(idParqueadero, fechaInicio, fechaFin);
        return historiales.stream()
                .filter(historial -> historial.getSalida() != null)
                .map(HistorialVehiculos::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    public BigDecimal calcularGananciasDelDia(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NotFoundException(PARQUEADERO_NO_ENCONTRADO));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inicioDia = now.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);

        return calcularGananciasPorPeriodo(inicioDia, finDia, parqueadero.getId());
    }

    public BigDecimal calcularGananciasDelMes(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NotFoundException(PARQUEADERO_NO_ENCONTRADO));

        LocalDate now = LocalDate.now();
        LocalDate inicioMes = now.withDayOfMonth(1);
        LocalDate finMes = now.plusMonths(1).withDayOfMonth(1);

        return calcularGananciasPorPeriodo(inicioMes.atStartOfDay(), finMes.atStartOfDay(), parqueadero.getId());
    }

    public BigDecimal calcularGananciasDelAnio(int idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NotFoundException(PARQUEADERO_NO_ENCONTRADO));

        LocalDate now = LocalDate.now();
        LocalDate inicioAnio = now.withDayOfYear(1);
        LocalDate finAnio = now.plusYears(1).withDayOfYear(1);

        return calcularGananciasPorPeriodo(inicioAnio.atStartOfDay(), finAnio.atStartOfDay(), parqueadero.getId());
    }

    public Page<ParqueaderoResponseDTO> obtenerParqueaderosExistentes(Pageable pageable) {
        Page<Parqueadero> parqueaderos = parqueaderoRepository.findAll(pageable);
        List<ParqueaderoResponseDTO> listParqueadero = parqueaderos.getContent().stream().map(p -> modelMapper.map(p, ParqueaderoResponseDTO.class)).toList();
        return new PageImpl<>(listParqueadero, pageable, parqueaderos.getTotalElements());
    }

    public Optional<Parqueadero> findById(int idParqueadero){
        return parqueaderoRepository.findById(idParqueadero);
    }


    public Page<Parqueadero> findByIdSocio(int idUsuario, Pageable pageable) {
        Page<Parqueadero> parqueaderos = parqueaderoRepository.findBySocio_Id(idUsuario, pageable);
        if(parqueaderos.isEmpty()){
            throw new NotFoundException("El usuario con id " + idUsuario + " no fue encontrado");
        }
                return parqueaderos;
    }

}






