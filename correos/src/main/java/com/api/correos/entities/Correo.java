package com.api.correos.entities;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document("correos")
@JsonPropertyOrder({"id", "email", "placa", "mensaje", "nombre_parqueadero, entrada"})
public class Correo {

    @Id
    private String id;
    private String email;
    private String placa;
    private String mensaje;
    @Field(name = "nombre_parqueadero")
    private String nombre;
    private LocalDateTime entrada;
}


