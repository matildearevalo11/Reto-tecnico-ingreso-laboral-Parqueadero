package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.feignclients.dto.CorreoRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class CorreoController {
    private static final Logger logger = LoggerFactory.getLogger(CorreoController.class);

    @PostMapping("/send-email")
    public String enviarCorreo(@RequestBody CorreoRequestDTO correoRequest) {
        logger.info("Solicitud de correo recibida: {}.", correoRequest);
        return "Correo Enviado";
    }
}