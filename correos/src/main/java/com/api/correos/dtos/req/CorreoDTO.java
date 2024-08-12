package com.api.correos.dtos.req;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorreoDTO {


    private String email;
    private String placa;
    private String mensaje;
    private String nombre;
}
