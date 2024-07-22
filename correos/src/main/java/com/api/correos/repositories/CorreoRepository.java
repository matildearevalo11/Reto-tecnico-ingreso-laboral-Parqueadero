package com.api.correos.repositories;

import com.api.correos.entities.Correo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreoRepository extends JpaRepository<Correo, Integer> {
}
