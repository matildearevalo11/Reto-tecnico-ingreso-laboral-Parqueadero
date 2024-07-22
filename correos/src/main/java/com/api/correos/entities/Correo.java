package com.api.correos.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name= "correo")
public class Correo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "placa")
    private String placa;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "id_parqueadero")
    private int idParqueadero;
}
