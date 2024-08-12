package com.api.correos.services;

import com.api.correos.dtos.req.CorreoDTO;
import com.api.correos.dtos.req.FechaRequestDTO;
import com.api.correos.dtos.res.IndicadoresParqueaderoResponseDTO;
import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.dtos.res.IngresosMesResponseDTO;
import com.api.correos.entities.Correo;
import com.api.correos.repositories.CorreoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorreoService {

    private final CorreoRepository correoRepository;

    public Correo enviarCorreo(CorreoDTO correoDTO) {
        Correo correo = new Correo();
        correo.setEmail(correoDTO.getEmail());
        correo.setPlaca(correoDTO.getPlaca());
        correo.setMensaje(correoDTO.getMensaje());
        correo.setNombre(correoDTO.getNombre());
        correo.setEntrada(correoDTO.getEntrada());
        return correoRepository.save(correo);
    }
}






