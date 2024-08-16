package com.prueba.pruebaparqueadero.services.dtos.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "El email es obligatorio.")
    String email;
    @NotBlank(message = "La contraseña es obligatoria.")
    String contrasenia;
}
