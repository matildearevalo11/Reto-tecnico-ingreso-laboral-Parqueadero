package com.prueba.pruebaparqueadero.repositories;

import com.prueba.pruebaparqueadero.entities.Parqueadero;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
    Page<Parqueadero> findBySocio_Id(int idUsuario, Pageable pageable);

}

