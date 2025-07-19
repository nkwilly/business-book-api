package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Agency;
import com.business.book.infrastructure.entity.Certification;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface CertificationRepository extends ReactiveCassandraRepository<Certification, UUID> {
    @Query("SELECT * FROM certifications WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<Certification> findByOrganizationId(UUID organizationId);
}

