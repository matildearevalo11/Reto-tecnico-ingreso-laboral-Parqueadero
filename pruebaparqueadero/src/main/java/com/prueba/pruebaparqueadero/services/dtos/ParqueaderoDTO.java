package com.prueba.pruebaparqueadero.services.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ParqueaderoDTO {
    @NotNull(message = "El id del socio es obligatorio.")
    private int idSocio;
    @NotBlank(message = "El nombre del parqueadero es obligatorio.")
    private String nombre;
    @NotBlank(message = "La dirección del parqueadero es obligatorio.")
    private String direccion;
    @NotNull(message = "La capacidad vehicular del parqueadero no puede estar vacía.")
    private int capacidadVehicular;
    @NotNull(message = "El costo de hora no puede estar vacío.")
    private BigDecimal costoHora;

}