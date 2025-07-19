package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.ReviewVisitor;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewVisitorRepository extends ReactiveCassandraRepository<ReviewVisitor, UUID> {
    Flux<ReviewVisitor> findByReviewId(UUID reviewId);
    Flux<ReviewVisitor> findByVisitorId(UUID visitorId);
    Mono<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId);
}