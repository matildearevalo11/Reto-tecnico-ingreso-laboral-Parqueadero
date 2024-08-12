package com.prueba.pruebaparqueadero.services.dtos.res;
import lombok.Data;

@Data
public class VehiculoResponseDTO {

    private String placa;
    private int idParqueadero;
    private String marca;
    private String modelo;
}
