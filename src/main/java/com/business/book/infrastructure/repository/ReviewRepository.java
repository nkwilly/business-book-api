package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.Review;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ReviewRepository extends ReactiveCassandraRepository<Review, UUID> {
    @Query("SELECT * FROM review WHERE organization_id = ?0 ALLOW FILTERING")
    Flux<Review> findByOrganizationId(UUID organizationId);
}