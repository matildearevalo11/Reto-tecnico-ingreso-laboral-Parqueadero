package com.api.correos.repositories;

import com.api.correos.dtos.res.IndicadoresParqueaderoResponseDTO;
import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.entities.Correo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ParqueaderoRepository extends MongoRepository<Correo, Integer> {

    @Aggregation(pipeline = {
            "{ $match: { 'entrada': { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: '$nombre_parqueadero', cantidadIngresos: { $sum: 1 } } }",
            "{ $sort: { cantidadIngresos: -1 } }",
            "{ $limit: 5 }",
            "{ $project: { nombreParqueadero: '$_id', cantidadIngresos: 1 } }"


    })
    List<IndicadoresParqueaderoResponseDTO> findTop5ParqueaderosMasIngresosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$nombre_parqueadero', cantidadIngresos: { $sum: 1 } } }",
            "{ $sort: { cantidadIngresos: -1 } }",
            "{ $limit: 5 }",
            "{ $project: { nombreParqueadero: '$_id', cantidadIngresos: 1 } }"


    })
    List<IndicadoresParqueaderoResponseDTO> findTop5ParqueaderosMasIngresos();




}
