package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.UserRole;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRoleRepository extends ReactiveCassandraRepository<UserRole, UUID> {
    @Query("SELECT * FROM user_roles WHERE user_id = ?0 ALLOW FILTERING")
    Flux<UserRole> findByUserId(UUID userId);

    Mono<UserRole> findByRoleId(UUID roleId);
}