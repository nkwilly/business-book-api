package com.business.book.repository;

import com.business.book.entity.Review;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends ReactiveCassandraRepository<Review, UUID> {
    Flux<Review> findByOrganizationId(UUID organizationId);
}