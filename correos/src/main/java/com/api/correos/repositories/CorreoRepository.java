package com.api.correos.repositories;

import com.api.correos.dtos.res.IndicadoresVehiculoResponseDTO;
import com.api.correos.entities.Correo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CorreoRepository extends MongoRepository<Correo, Integer> {




}


