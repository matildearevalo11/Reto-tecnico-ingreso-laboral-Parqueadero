package com.prueba.pruebaparqueadero.repositories;

import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    Optional<Vehiculo> findByPlaca(String placa);

    @Query("SELECT v FROM Vehiculo v WHERE v.placa LIKE %:placaCoincidencia%")
    List<Vehiculo> findByPlacaCoincidencia(@Param("placaCoincidencia") String placaCoincidencia);

    @Query("SELECT v FROM Vehiculo v JOIN HistorialVehiculos h ON v.id = h.vehiculo.id WHERE v.parqueadero.id = :idParqueadero AND h.salida IS NULL")
    List<Vehiculo> findByPorParqueadero(@Param("idParqueadero") int idParqueadero);


}
