package com.prueba.pruebaparqueadero.repositories;

import com.prueba.pruebaparqueadero.entities.HistorialVehiculos;
import com.prueba.pruebaparqueadero.entities.Parqueadero;
import com.prueba.pruebaparqueadero.entities.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistorialVehiculosRepository extends JpaRepository<HistorialVehiculos, Integer> {
    Optional<HistorialVehiculos> findByVehiculoAndSalidaIsNull(Vehiculo vehiculo);

    @Query("SELECT h FROM HistorialVehiculos h WHERE h.parqueadero.id = :idParqueadero AND h.entrada >= :fechaInicio AND h.entrada < :fechaFin")
    List<HistorialVehiculos> findByEntradaBetween(@Param("idParqueadero") int idParqueadero,
                                                  @Param("fechaInicio") LocalDateTime startDate,
                                                  @Param("fechaFin") LocalDateTime endDate);


    @Query("SELECT h FROM HistorialVehiculos h WHERE h.parqueadero.id = :idParqueadero AND h.salida IS NULL")
    Page<HistorialVehiculos> findByPorParqueadero(@Param("idParqueadero") int idParqueadero, Pageable pageable);

    @Query("SELECT h.vehiculo.placa, COUNT(h) FROM HistorialVehiculos h GROUP BY h.vehiculo ORDER BY COUNT(h) DESC")
    List<Object[]>findVehiculosMasRegistrados();

    @Query("SELECT h.vehiculo.placa, COUNT(h) FROM HistorialVehiculos h WHERE h.parqueadero.id = :idParqueadero GROUP BY h.vehiculo ORDER BY COUNT(h) DESC")
    List<Object[]>findVehiculosMasRegistradosPorParqueadero(@Param("idParqueadero") int idParqueadero);

    @Query("SELECT COUNT(h) FROM HistorialVehiculos h WHERE h.vehiculo.placa = :placa AND h.parqueadero.id = :idParqueadero")
    long countEntriesByPlacaAndParqueadero(@Param("placa") String placa, @Param("idParqueadero") int idParqueadero);

    long countByParqueaderoAndSalidaIsNull(Parqueadero parqueadero);

}
