package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.auth.AuthResponse;
import com.prueba.pruebaparqueadero.auth.LoginRequest;
import com.prueba.pruebaparqueadero.auth.RegisterRequest;
import com.prueba.pruebaparqueadero.services.AuthService;
import com.prueba.pruebaparqueadero.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;


    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "logout")
    public String logout(){
        return "logout";
    }

}
