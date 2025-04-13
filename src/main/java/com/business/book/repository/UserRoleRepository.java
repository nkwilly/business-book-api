package com.business.book.repository;

import com.business.book.entity.UserRole;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends CassandraRepository<UserRole, UUID> {
    List<UserRole> findByUserId(UUID userId);

    Optional<UserRole> findByRoleId(UUID roleId);
}
