package com.business.book.domain.service;

import com.business.book.infrastructure.entity.ReviewNote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewNoteService {
    Mono<ReviewNote> save(ReviewNote reviewNote);
    Flux<ReviewNote> findAll();
    Mono<ReviewNote> findById(UUID id);
    Mono<Void> deleteById(UUID id);
}