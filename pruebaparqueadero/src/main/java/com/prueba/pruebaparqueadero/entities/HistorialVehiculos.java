package com.prueba.pruebaparqueadero.entities;

import jakarta.persistence.*;
import lombok.Data;
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
    private Parqueadero parqueadero;

    @ManyToOne
    @JoinColumn(referencedColumnName = "placa",  nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Column(name = "salida")
    private LocalDateTime salida;

}
