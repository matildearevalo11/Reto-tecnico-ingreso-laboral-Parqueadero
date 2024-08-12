package com.prueba.pruebaparqueadero.services.dtos.res;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParqueaderoResponseDTO {
    private int idSocio;
    private String nombre;
    private String direccion;
    private int capacidadVehicular;
    private BigDecimal costoHora;

}
