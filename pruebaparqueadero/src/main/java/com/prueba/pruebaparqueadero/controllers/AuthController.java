package com.prueba.pruebaparqueadero.controllers;

import com.prueba.pruebaparqueadero.auth.AuthResponse;
import com.prueba.pruebaparqueadero.auth.LoginRequest;
import com.prueba.pruebaparqueadero.auth.RegisterRequest;
import com.prueba.pruebaparqueadero.services.AuthService;
import com.prueba.pruebaparqueadero.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

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

    @GetMapping("/token-duracion")
    public ResponseEntity<?> obtenerDuracionToken(@RequestParam String token) {
        try {
            long duracion = jwtService.calcularDuracionToken(token);
            return ResponseEntity.ok(Collections.singletonMap("duracion", duracion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("mensaje", "Token inválido o expirado"));
        }
    }
}
