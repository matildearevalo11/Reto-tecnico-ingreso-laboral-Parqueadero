package com.prueba.pruebaparqueadero.services.dtos;
import com.prueba.pruebaparqueadero.entities.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private int id;
    private String email;
    private String contrasenia;
    private String nombre;
    private Rol rol;
}
