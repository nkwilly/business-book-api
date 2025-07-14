package com.business.book.controller;

import com.business.book.entity.Review;
import com.business.book.repository.ReviewRepository;
import com.business.book.service.ReviewService;
import com.business.book.service.payload.request.CreateReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<Review> createReview(@RequestBody CreateReviewRequest createReviewRequest) {
        Review review = Review.builder()
                .id(UUID.randomUUID())
                .organizationId(createReviewRequest.getOrganizationId())
                .content(createReviewRequest.getContent())
                .build();
        return reviewService.save(review);
    }

    @GetMapping
    public Flux<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Review> getReviewById(@PathVariable UUID id) {
        return reviewService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<Review> updateReview(@PathVariable UUID id, @RequestBody Review review) {
        return reviewService.findById(id)
                .flatMap(existingReview -> {
                    review.setId(id);
                    return reviewService.save(review);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<Void> deleteReview(@PathVariable UUID id) {
        return reviewService.findById(id)
                .flatMap(review -> {
                    return reviewService.deleteById(id);
                });
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<Review> getReviewsByOrganizationId(@PathVariable UUID organizationId) {
        return reviewRepository.findByOrganizationId(organizationId);
    }
}