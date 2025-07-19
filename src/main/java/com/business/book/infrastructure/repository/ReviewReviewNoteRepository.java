package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.ReviewReviewNote;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewReviewNoteRepository extends ReactiveCassandraRepository<ReviewReviewNote, UUID> {
    Flux<ReviewReviewNote> findByReviewId(UUID reviewId);
    Flux<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId);
    Mono<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId);
}