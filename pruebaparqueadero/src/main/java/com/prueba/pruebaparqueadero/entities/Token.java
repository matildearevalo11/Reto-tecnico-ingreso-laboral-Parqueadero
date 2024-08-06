package com.prueba.pruebaparqueadero.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Usuario idUsuario;
    @Column(name = "jwt_token")
    private String tokenJwt;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revoked")
    private boolean revoked;
}


