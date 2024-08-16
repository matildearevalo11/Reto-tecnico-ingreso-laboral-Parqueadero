package com.prueba.pruebaparqueadero.services.dtos.res;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParqueaderoResponseDTO {
    private String nombreSocio;
    private String nombreParqueadero;
    private String direccion;
    private int capacidadVehicular;
    private BigDecimal costoHora;

}
