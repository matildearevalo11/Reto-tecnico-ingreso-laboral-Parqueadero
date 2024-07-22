package com.prueba.pruebaparqueadero.feignclients;
import com.prueba.pruebaparqueadero.feignclients.dto.CorreoRequestDTO;
import com.prueba.pruebaparqueadero.feignclients.dto.RespuestaDTO;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "correos", url="http://localhost:8082/correo")
public interface CorreoFeignClients {

    @PostMapping
    ResponseEntity<RespuestaDTO> enviarCorreo(@Valid @RequestBody CorreoRequestDTO correoRequestDTO);
}

