package com.business.book.repository;

import com.business.book.entity.Enterprise;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface EnterpriseRepository extends ReactiveCassandraRepository<Enterprise, UUID> {
}