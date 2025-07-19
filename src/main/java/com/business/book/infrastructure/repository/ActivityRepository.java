package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Activity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ActivityRepository extends ReactiveCassandraRepository<Activity, UUID> {
    @Query("SELECT * FROM activity WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<Activity> findByOrganizationId(UUID organizationId);
}

