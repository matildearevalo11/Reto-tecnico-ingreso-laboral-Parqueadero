package com.prueba.pruebaparqueadero.services.dtos;

import lombok.Data;

@Data
public class CorreoDTO {
    private String email;
    private String placa;
    private String mensaje;
    private int idParqueadero;
}

