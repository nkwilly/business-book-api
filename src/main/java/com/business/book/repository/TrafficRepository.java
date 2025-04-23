package com.business.book.repository;

import com.business.book.entity.Traffic;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrafficRepository extends CassandraRepository<Traffic, UUID> {

    Optional<Traffic> findByEnterpriseId(UUID enterpriseId);
}
