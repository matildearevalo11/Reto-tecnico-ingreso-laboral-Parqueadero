package com.prueba.pruebaparqueadero.entities;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
@Table(name= "vehiculo")
public class Vehiculo {

    @Id
    @Column(name = "placa", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$")
    private String placa;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parqueadero parqueadero;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "color", nullable = false)
    private String color;


}
