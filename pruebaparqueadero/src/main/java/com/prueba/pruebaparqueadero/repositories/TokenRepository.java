package com.prueba.pruebaparqueadero.repositories;
import com.prueba.pruebaparqueadero.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(nativeQuery = true, value = "SELECT t.* FROM tokens t JOIN usuarios u ON (t.user_id=u.id) WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByTokenJwt(String token);
}
