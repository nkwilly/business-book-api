package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Prospect;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProspectRepository extends ReactiveCassandraRepository<Prospect, UUID> {
    @Query("SELECT * FROM practical_information WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<Prospect> findByOrganizationId(UUID organizationId);

}

