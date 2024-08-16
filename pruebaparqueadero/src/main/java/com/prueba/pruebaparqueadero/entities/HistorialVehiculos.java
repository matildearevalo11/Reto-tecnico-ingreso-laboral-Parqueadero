package com.prueba.pruebaparqueadero.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name= "historial_vehiculos")
public class HistorialVehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parqueadero parqueadero;

    @ManyToOne
    @JoinColumn(referencedColumnName = "placa",  nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Column(name = "salida")
    private LocalDateTime salida;

    @Column(name = "total")
    private BigDecimal total;

}