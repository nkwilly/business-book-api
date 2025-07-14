package com.business.book.service;

import com.business.book.entity.ReviewVisitor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewVisitorService {
    Mono<ReviewVisitor> save(ReviewVisitor reviewVisitor);
    Flux<ReviewVisitor> findAll();
    Mono<ReviewVisitor> findById(UUID id);
    Flux<ReviewVisitor> findByReviewId(UUID reviewId);
    Flux<ReviewVisitor> findByVisitorId(UUID visitorId);
    Mono<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId);
    Mono<Void> deleteById(UUID id);
}