package com.prueba.pruebaparqueadero.services.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VehiculosParqueadosDTO {
    private int id;
    private String placa;
    private LocalDateTime fechaIngreso;
}
