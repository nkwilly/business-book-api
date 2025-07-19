package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Role;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoleRepository extends ReactiveCassandraRepository<Role, UUID> {
    @Query("SELECT * FROM roles WHERE name = ?0 ALLOW FILTERING")
    Mono<Role> findByName(String name);
}
