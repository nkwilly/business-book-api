package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.PracticalInformation;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PracticalInformationRepository extends ReactiveCassandraRepository<PracticalInformation, UUID> {
    @Query("SELECT * FROM practical_information WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<PracticalInformation> findByOrganizationId(UUID organizationId);
}