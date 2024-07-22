package com.prueba.pruebaparqueadero.repositories;

import com.prueba.pruebaparqueadero.entities.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
    List<Parqueadero> findBySocio_Id(int idSocio);
}

