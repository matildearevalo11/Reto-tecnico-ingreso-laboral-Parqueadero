package com.prueba.pruebaparqueadero.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/correoS")
public class CorreoController {
    private static final Logger logger = LoggerFactory.getLogger(CorreoController.class);


    @PostMapping("/enviar-correo")
    public ResponseEntity<Map<String, String>> enviarCorreo(@RequestBody Map<String, String> correoRequest) {
        logger.error("Solicitud de correo recibida: {}.", correoRequest);
        return ResponseEntity.ok(Map.of("mensaje", "Correo Enviado"));
    }
}
