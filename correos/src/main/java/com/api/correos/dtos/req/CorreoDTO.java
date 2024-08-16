package com.api.correos.dtos.req;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CorreoDTO {


    private String email;
    private String placa;
    private String mensaje;
    private String nombre;
    private LocalDateTime entrada;
}
