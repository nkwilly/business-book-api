package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.Token;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends ReactiveCassandraRepository<Token, UUID> {

    default Mono<Token> customSave(Token token) {
        Mono<Token> existToken = findById(token.getId());
        existToken.blockOptional().ifPresent(this::delete);
        return save(token);
    }

    Mono<Token> findTopByOrderBySaveAtDesc();
}
