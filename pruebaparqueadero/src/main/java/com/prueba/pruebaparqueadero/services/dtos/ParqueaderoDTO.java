package com.prueba.pruebaparqueadero.services.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;

@Data
public class ParqueaderoDTO {
    private int idSocio;
    private String nombre;
    private String direccion;
    private String capacidadVehicular;
    private BigDecimal costoHora;

}