package com.prueba.pruebaparqueadero.services.dtos;
import com.prueba.pruebaparqueadero.entities.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {
    private int id;
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria.")
    private String contrasenia;
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String nombre;
    private Rol rol;
}
