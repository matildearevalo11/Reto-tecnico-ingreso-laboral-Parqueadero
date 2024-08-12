package com.prueba.pruebaparqueadero.services.dtos.res;

import lombok.Data;

import java.time.LocalDateTime;
@Data

public class VehiculosParqueaderoResponseDTO {
    private int id;
    private VehiculoResponseDTO vehiculo;
    private LocalDateTime entrada;
}
