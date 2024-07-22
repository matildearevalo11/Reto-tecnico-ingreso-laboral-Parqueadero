package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.auth.AuthResponse;
import com.prueba.pruebaparqueadero.auth.LoginRequest;
import com.prueba.pruebaparqueadero.auth.RegisterRequest;
import com.prueba.pruebaparqueadero.entities.Rol;
import com.prueba.pruebaparqueadero.entities.Usuario;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Data
@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia()));
        UserDetails user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode( request.getContrasenia()))
                .nombre(request.getNombre())
                .rol(Rol.SOCIO)
                .build();

        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }

}