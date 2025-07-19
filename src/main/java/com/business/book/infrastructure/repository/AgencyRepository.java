package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Activity;
import com.business.book.infrastructure.entity.Agency;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AgencyRepository extends ReactiveCassandraRepository<Agency, UUID> {
    @Query("SELECT * FROM agency WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<Agency> findByOrganizationId(UUID organizationId);
}

