package com.prueba.pruebaparqueadero.services.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculoDTO {

    private String placa;
    private int idParqueadero;
    private String marca;
    private String modelo;
    private String color;
}
