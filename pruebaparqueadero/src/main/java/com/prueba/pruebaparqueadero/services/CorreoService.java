package com.prueba.pruebaparqueadero.services;

import com.prueba.pruebaparqueadero.services.dtos.CorreoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CorreoService {

    private final RestTemplate restTemplate;
    private static final String CORREO_SERVICE_URL = "http://localhost:8082/api/correos";

    public void enviarCorreo(CorreoDTO correoDTO) {
        restTemplate.postForEntity(CORREO_SERVICE_URL, correoDTO, Void.class);
    }
}
