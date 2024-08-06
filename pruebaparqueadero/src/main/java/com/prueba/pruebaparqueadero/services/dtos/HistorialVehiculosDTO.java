package com.prueba.pruebaparqueadero.services.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialVehiculosDTO {

    @NotNull(message = "El id del historial es obligatorio.")
    private int id;
    @NotNull(message = "El id del parqueadero es obligatorio.")
    private int idParqueadero;
    @NotNull(message = "El id del vehiculo es obligatorio.")
    private int idVehiculo;
    private LocalDateTime entrada;
    private LocalDateTime salida;
}
