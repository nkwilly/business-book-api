package com.business.book.repository;

import com.business.book.entity.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface RoleRepository extends CassandraRepository<Role, UUID> {
}
