package com.prueba.pruebaparqueadero.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/correo")
public class CorreoController {

    @PostMapping("/enviar-correo")
    public ResponseEntity<Map<String, String>> enviarCorreo(@RequestBody Map<String, String> correoRequest) {
        System.out.println("Solicitud de correo recibida: " + correoRequest);
        return ResponseEntity.ok(Map.of("mensaje", "Correo Enviado"));
    }
}
