package com.prueba.pruebaparqueadero.feignclients.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorreoRequestDTO {


    private String email;
    private String placa;
    private String mensaje;
    private int idParqueadero;
}