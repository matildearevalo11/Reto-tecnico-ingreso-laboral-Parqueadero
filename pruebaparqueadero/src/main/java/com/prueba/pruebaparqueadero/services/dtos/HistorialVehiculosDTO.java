package com.prueba.pruebaparqueadero.services.dtos;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialVehiculosDTO {
    private int id;
    private int idParqueadero;
    private int idVehiculo;
    private LocalDateTime entrada;
    private LocalDateTime salida;
}
