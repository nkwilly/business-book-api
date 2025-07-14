package com.business.book.repository;

import com.business.book.entity.Role;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoleRepository extends ReactiveCassandraRepository<Role, UUID> {
    Mono<Role> findByName(String name);
}
