package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.services.dtos.res.AuthResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.LoginRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.RegisterRequestDTO;
import com.prueba.pruebaparqueadero.services.AuthService;
import com.prueba.pruebaparqueadero.services.dtos.res.MensajeResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping(value = "login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

    @PostMapping(value = "register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MensajeResponseDTO register(@Valid @RequestBody RegisterRequestDTO request){
        authService.register(request);
        return new MensajeResponseDTO("El socio  "+request.getNombre()+" ha sido creado");
    }

    @PostMapping(value = "logout")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public MensajeResponseDTO logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authService.logout(authHeader);
        }
        return new MensajeResponseDTO("Su sesión ha caducado");
    }

}
