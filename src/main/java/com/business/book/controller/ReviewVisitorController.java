package com.business.book.controller;

import com.business.book.entity.ReviewVisitor;
import com.business.book.service.ReviewVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review-visitors")
public class ReviewVisitorController {
    
    private final ReviewVisitorService reviewVisitorService;
    
    @Autowired
    public ReviewVisitorController(ReviewVisitorService reviewVisitorService) {
        this.reviewVisitorService = reviewVisitorService;
    }
    
    @PostMapping
    public Mono<ReviewVisitor> createReviewVisitor(@RequestBody ReviewVisitor reviewVisitor) {
        return reviewVisitorService.save(reviewVisitor);
    }
    
    @GetMapping
    public Flux<ReviewVisitor> getAllReviewVisitors() {
        return reviewVisitorService.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<ReviewVisitor> getReviewVisitorById(@PathVariable UUID id) {
        return reviewVisitorService.findById(id);
    }
    
    @GetMapping("/review/{reviewId}")
    public Flux<ReviewVisitor> getReviewVisitorsByReviewId(@PathVariable UUID reviewId) {
        return reviewVisitorService.findByReviewId(reviewId);
    }
    
    @GetMapping("/visitor/{visitorId}")
    public Flux<ReviewVisitor> getReviewVisitorsByVisitorId(@PathVariable UUID visitorId) {
        return reviewVisitorService.findByVisitorId(visitorId);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> deleteReviewVisitor(@PathVariable UUID id) {
        return reviewVisitorService.deleteById(id);
    }
}