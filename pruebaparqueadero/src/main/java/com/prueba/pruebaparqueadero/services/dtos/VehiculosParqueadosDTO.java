package com.prueba.pruebaparqueadero.services.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VehiculosParqueadosDTO {

    @NotNull(message = "El id es obligatorio.")
    private int id;
    @NotBlank(message = "El número de placa es obligatorio.")
    private String placa;
    private LocalDateTime fechaIngreso;
}
