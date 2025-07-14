package com.business.book.repository;

import com.business.book.entity.ReviewReviewNote;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewReviewNoteRepository extends ReactiveCassandraRepository<ReviewReviewNote, UUID> {
    Flux<ReviewReviewNote> findByReviewId(UUID reviewId);
    Flux<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId);
    Mono<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId);
}