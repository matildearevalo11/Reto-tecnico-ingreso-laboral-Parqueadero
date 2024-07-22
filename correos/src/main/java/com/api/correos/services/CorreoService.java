package com.api.correos.services;

import com.api.correos.dtos.CorreoDTO;
import com.api.correos.entities.Correo;
import com.api.correos.repositories.CorreoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorreoService {
    private final CorreoRepository correoRepository;

        public Correo enviarCorreo(CorreoDTO correoDTO) {
            Correo correo = new Correo();
            correo.setEmail(correoDTO.getEmail());
            correo.setPlaca(correoDTO.getPlaca());
            correo.setMensaje(correoDTO.getMensaje());
            correo.setIdParqueadero(correoDTO.getIdParqueadero());
            return correoRepository.save(correo);
        }



    private void guardarCorreo(CorreoDTO correo) {
        Correo correoGuardado = new Correo();
        correoGuardado.setEmail(correo.getEmail());
        correoGuardado.setPlaca(correo.getPlaca());
        correoGuardado.setMensaje(correo.getMensaje());
        correoGuardado.setIdParqueadero(correo.getIdParqueadero());
        correoRepository.save(correoGuardado);
    }
}
