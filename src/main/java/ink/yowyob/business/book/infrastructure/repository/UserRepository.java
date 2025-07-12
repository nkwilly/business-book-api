package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByTel(String tel);
}