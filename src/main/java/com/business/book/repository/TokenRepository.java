package com.business.book.repository;

import com.business.book.entity.Token;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends ReactiveCassandraRepository<Token, UUID> {

    default Mono<Token> customSave(Token token) {
        Mono<Token> existToken = findById(token.getId());
        if (existToken.blockOptional().isPresent()) {
            this.deleteById(token.getId());
        }
        return save(token);
    }

    Mono<Token> findTopByOrderBySaveAtDesc();
}
