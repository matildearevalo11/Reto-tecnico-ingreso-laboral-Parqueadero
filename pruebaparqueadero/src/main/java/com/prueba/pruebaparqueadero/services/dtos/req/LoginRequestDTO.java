package com.prueba.pruebaparqueadero.services.dtos.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    String email;
    String contrasenia;
}
