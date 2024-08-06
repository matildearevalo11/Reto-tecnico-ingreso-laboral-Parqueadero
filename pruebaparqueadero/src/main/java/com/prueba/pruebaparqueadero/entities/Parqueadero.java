package com.prueba.pruebaparqueadero.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name= "parqueadero")
public class Parqueadero {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_socio", nullable = false)
    private Usuario socio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "capacidad_vehicular")
    private int capacidadVehicular;

    @Column(name = "costo_hora")
    private BigDecimal costoHora;

}
