package com.prueba.pruebaparqueadero.feignclients.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CorreoRequestDTO {


    private String email;
    private String placa;
    private String mensaje;
    private String nombre;
    private LocalDateTime entrada;
}