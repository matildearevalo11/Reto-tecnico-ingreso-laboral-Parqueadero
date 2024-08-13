package com.prueba.pruebaparqueadero.controllers;
import com.prueba.pruebaparqueadero.services.ParqueaderoService;
import com.prueba.pruebaparqueadero.services.dtos.req.ParqueaderoRequestDTO;
import com.prueba.pruebaparqueadero.services.dtos.res.ParqueaderoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkings")
@RequiredArgsConstructor
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;
    private static final String MESSAGE = "message";

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ParqueaderoResponseDTO crearParqueadero(@RequestBody ParqueaderoRequestDTO parqueaderoDto) {
        return parqueaderoService.crearParqueadero(parqueaderoDto, parqueaderoDto.getIdSocio());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ParqueaderoResponseDTO obtenerParqueadero(@PathVariable int id) {
        return parqueaderoService.obtenerParqueadero(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ParqueaderoResponseDTO actualizarParqueadero(@PathVariable int id, @RequestBody ParqueaderoRequestDTO parqueadero) {
        return parqueaderoService.actualizarParqueadero(id, parqueadero);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminarParqueadero(@PathVariable int id) {
        parqueaderoService.eliminarParqueadero(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ParqueaderoResponseDTO> obtenerParqueaderosExistentes(Pageable pageable) {
        return parqueaderoService.obtenerParqueaderosExistentes(pageable);
    }

}




