package com.prueba.pruebaparqueadero.services.dtos.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParqueaderoRequestDTO {
    @NotNull(message = "El id del socio es obligatorio.")
    private int idSocio;
    @NotBlank(message = "El nombre del parqueadero es obligatorio.")
    private String nombre;
    @NotBlank(message = "La dirección del parqueadero es obligatorio.")
    private String direccion;
    @NotNull(message = "La capacidad vehicular del parqueadero no puede estar vacía.")
    @Min(value = 1, message = "La capacidad vehicular debe ser mayor a 0.")
    private int capacidadVehicular;
    @NotNull(message = "El costo por hora no puede estar vacío.")
    @Min(value = 1, message = "El costo por hora debe ser mayor que 0")
    private BigDecimal costoHora;

}