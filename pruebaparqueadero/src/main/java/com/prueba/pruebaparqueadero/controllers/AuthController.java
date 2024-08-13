package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.services.dtos.res.AuthResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.LoginRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.RegisterRequestDTO;
import com.prueba.pruebaparqueadero.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping(value = "login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

    @PostMapping(value = "register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request){
        return authService.register(request);
    }

    @PostMapping(value = "logout")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('SOCIO')")
    public String logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authService.logout(authHeader);
        }
        return "Logout";
    }

}
