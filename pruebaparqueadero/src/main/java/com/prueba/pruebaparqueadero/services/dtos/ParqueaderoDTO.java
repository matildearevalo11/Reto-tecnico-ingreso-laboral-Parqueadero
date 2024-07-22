package com.prueba.pruebaparqueadero.services.dtos;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ParqueaderoDTO {
    private int id;
    private int socio;
    private String nombre;
    private String direccion;
    private String capacidadVehicular;
    private BigDecimal costoHora;
}