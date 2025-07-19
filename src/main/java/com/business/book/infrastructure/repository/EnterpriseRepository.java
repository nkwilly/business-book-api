package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Enterprise;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface EnterpriseRepository extends ReactiveCassandraRepository<Enterprise, UUID> {
}