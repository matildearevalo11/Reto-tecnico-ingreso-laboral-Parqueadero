package com.api.correos.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorreoDTO {


    private String email;
    private String placa;
    private String mensaje;
    private int idParqueadero;
}
