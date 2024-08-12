package com.api.correos.repositories;

import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.entities.Correo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VehiculoRepository extends MongoRepository<Correo, Integer> {

    int countByEntradaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Aggregation(pipeline = {
            "{ $match: { 'entrada': { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: '$placa', cantidadingresos: { $sum: 1 } } }",
            "{ $sort: { cantidadingresos: -1 } }",
            "{ $limit: 5 }",
            "{ $project: { placa: '$_id', cantidadingresos: 1 } }"
    })
    List<IndicadoresVehiculoResponseDTO> findTop5VehiculosMasIngresos(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$placa', cantidadingresos: { $sum: 1 } } }",
            "{ $sort: { cantidadingresos: -1 } }",
            "{ $limit: 5 }",
            "{ $project: { placa: '$_id', cantidadingresos: 1 } }"
    })
    List<IndicadoresVehiculoResponseDTO> findTop5VehiculosMasIngresosGeneral();

}
