package com.prueba.pruebaparqueadero.services.dtos.res;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistorialVehiculosResponseDTO {
    int id;
    String placa;
    int idParqueadero;
    LocalDateTime entrada;

}
