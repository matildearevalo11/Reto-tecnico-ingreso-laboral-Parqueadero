package com.prueba.pruebaparqueadero.repositories;
import com.prueba.pruebaparqueadero.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
    void deleteByExpirationBefore(Date now);
}
