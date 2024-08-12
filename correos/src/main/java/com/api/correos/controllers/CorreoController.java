package com.api.correos.controllers;

import com.api.correos.dtos.req.CorreoDTO;
import com.api.correos.entities.Correo;
import com.api.correos.services.CorreoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/correo")
@RequiredArgsConstructor
public class CorreoController {

    private final CorreoService correoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Correo enviarCorreo(@RequestBody @Valid CorreoDTO correoDTO) {
        return correoService.enviarCorreo(correoDTO);
    }
}

