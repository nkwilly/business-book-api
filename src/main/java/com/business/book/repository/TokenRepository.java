package com.business.book.repository;

import com.business.book.entity.Token;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CassandraRepository<Token, UUID> {
    default Token customSave(Token token) {
        Optional<Token> existToken = findById(token.getId());
        existToken.ifPresent(this::delete);
        return save(token);
    }

    Optional<Token> findTopByOrderBySaveAtDesc();
}
