package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.services.dtos.res.AuthResponseDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.LoginRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.req.RegisterRequestDTO;
import com.prueba.pruebaparqueadero.entities.Rol;
import com.prueba.pruebaparqueadero.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia()));
        UserDetails user=usuarioService.findByUserEmail(request.getEmail());
        String token=jwtService.getToken(user);
        return AuthResponseDTO.builder()
                .token(token)
                .build();

    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        Usuario user = Usuario.builder()
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode( request.getContrasenia()))
                .nombre(request.getNombre())
                .rol(Rol.SOCIO)
                .build();

        usuarioService.crearSocio(user);
        return AuthResponseDTO.builder().token(jwtService.getToken(user)).build();
    }

    public void logout(String authHeader) {
        String token = authHeader.substring(7);
        jwtService.revokeToken(token);
    }

}