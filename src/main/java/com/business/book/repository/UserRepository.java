package com.business.book.repository;

import com.business.book.entity.User;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByTel(String tel);
}