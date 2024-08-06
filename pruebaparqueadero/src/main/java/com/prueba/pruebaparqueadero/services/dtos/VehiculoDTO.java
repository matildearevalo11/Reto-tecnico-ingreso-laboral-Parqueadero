package com.prueba.pruebaparqueadero.services.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculoDTO {

    @NotBlank(message = "El número de placa es obligatorio.")
    private String placa;
    @NotNull(message = "El id del parqueadero es obligatorio.")
    private int idParqueadero;
    private String marca;
    private String modelo;
    private String color;
}
