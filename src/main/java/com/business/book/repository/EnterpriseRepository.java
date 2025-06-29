package com.business.book.repository;

import com.business.book.entity.Enterprise;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface EnterpriseRepository extends CassandraRepository<Enterprise, UUID> {
}