package com.api.correos.controllers;

import com.api.correos.dtos.CorreoDTO;
import com.api.correos.dtos.Respuesta;
import com.api.correos.entities.Correo;
import com.api.correos.services.CorreoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/correo")
@RequiredArgsConstructor
public class CorreoController {

    private final CorreoService correoService;


    @PostMapping
    public ResponseEntity<Map<String, String>> enviarCorreo(@RequestBody @Valid CorreoDTO correoDTO) {
        Correo correoEnviado = correoService.enviarCorreo(correoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("mensaje", "Correo enviado"));
    }




}

