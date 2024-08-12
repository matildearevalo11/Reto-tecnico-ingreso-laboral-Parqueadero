package com.prueba.pruebaparqueadero.services.dtos.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CorreoRequestDTO {
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String email;
    @NotBlank(message = "El número de placa es obligatorio.")
    private String placa;
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;
    @NotNull(message = "El id del parqueadero es obligatorio.")
    private int idParqueadero;
}

