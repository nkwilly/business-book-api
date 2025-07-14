package com.business.book.repository;

import com.business.book.entity.UserRole;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends ReactiveCassandraRepository<UserRole, UUID> {
    Flux<UserRole> findByUserId(UUID userId);

    Mono<UserRole> findByRoleId(UUID roleId);
}
