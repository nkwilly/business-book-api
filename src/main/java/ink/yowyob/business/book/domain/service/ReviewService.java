package ink.yowyob.business.book.domain.service;

import ink.yowyob.business.book.infrastructure.entity.Review;
import ink.yowyob.business.book.presentation.dto.review.CreateReviewDto;

import ink.yowyob.business.book.presentation.dto.review.UpdateReviewDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewService {
    Mono<Review> save(CreateReviewDto review);
    Flux<Review> findAll();
    Mono<Review> findById(UUID id);
    Mono<Review> update(UUID id, UpdateReviewDto review);
    Mono<Void> deleteById(UUID id);
}
