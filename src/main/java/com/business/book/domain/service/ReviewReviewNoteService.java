package com.business.book.domain.service;

import com.business.book.infrastructure.entity.ReviewReviewNote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewReviewNoteService {
    Mono<ReviewReviewNote> save(ReviewReviewNote reviewReviewNote);
    Flux<ReviewReviewNote> findAll();
    Mono<ReviewReviewNote> findById(UUID id);
    Flux<ReviewReviewNote> findByReviewId(UUID reviewId);
    Flux<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId);
    Mono<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId);
    Mono<Void> deleteById(UUID id);
}