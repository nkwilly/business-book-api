package com.business.book.service;

import com.business.book.entity.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewService {
    Mono<Review> save(Review review);
    Flux<Review> findAll();
    Mono<Review> findById(UUID id);
    Mono<Void> deleteById(UUID id);
}