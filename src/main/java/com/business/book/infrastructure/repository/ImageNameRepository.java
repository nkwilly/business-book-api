package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.ImageName;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.UUID;

public interface ImageNameRepository extends ReactiveCassandraRepository<ImageName, UUID> {
    @Query("SELECT * FROM image_name WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<ImageName> findByOrganizationId(UUID organizationId);

    Flux<ImageName> findByName(String name);
}

