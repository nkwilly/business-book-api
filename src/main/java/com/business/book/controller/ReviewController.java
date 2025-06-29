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
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewRequest createReviewRequest) {
        Review review = Review.builder()
                .id(UUID.randomUUID())
                .organizationId(createReviewRequest.getOrganizationId())
                .content(createReviewRequest.getContent())
                .build();
        Review savedReview = reviewService.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID id) {
        return reviewService.findById(id)
                .map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Review> updateReview(@PathVariable UUID id, @RequestBody Review review) {
        return reviewService.findById(id)
                .map(existingReview -> {
                    review.setId(id);
                    Review updatedReview = reviewService.save(review);
                    return new ResponseEntity<>(updatedReview, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        return reviewService.findById(id)
                .map(review -> {
                    reviewService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Review>> getReviewsByOrganizationId(@PathVariable UUID organizationId) {
        List<Review> reviews = reviewRepository.findByOrganizationId(organizationId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}