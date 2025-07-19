package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
    @Query("SELECT * FROM users WHERE username = ?0 ALLOW FILTERING")
    Mono<User> findByUsername(String username);

    @Query("SELECT * FROM users WHERE username = ?0 ALLOW FILTERING")
    Mono<User> findByUsernameForExistence(String username);

    default Mono<Boolean> existsByUsername(String username) {
        return findByUsernameForExistence(username)
                .map(user -> true)
                .defaultIfEmpty(false);
    }

    @Query("SELECT * FROM users WHERE email = ?0 ALLOW FILTERING")
    Mono<User> findByEmailForExistence(String email);

    default Mono<Boolean> existsByEmail(String email) {
        return findByEmailForExistence(email)
                .map(user -> true)
                .defaultIfEmpty(false);
    }

    @Query("SELECT * FROM users WHERE tel = ?0 ALLOW FILTERING")
    Mono<User> findByTelForExistence(String tel);

    default Mono<Boolean> existsByTel(String tel) {
        return findByTelForExistence(tel)
                .map(user -> true)
                .defaultIfEmpty(false);
    }
}