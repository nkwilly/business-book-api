package com.business.book.presentation.controller;

import com.business.book.infrastructure.entity.ReviewReviewNote;
import com.business.book.domain.service.ReviewReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/review-review-notes")
public class ReviewReviewNoteController {
    
    private final ReviewReviewNoteService reviewReviewNoteService;
    
    @Autowired
    public ReviewReviewNoteController(ReviewReviewNoteService reviewReviewNoteService) {
        this.reviewReviewNoteService = reviewReviewNoteService;
    }
    
    @PostMapping
    public Mono<ReviewReviewNote> createReviewReviewNote(@RequestBody ReviewReviewNote reviewReviewNote) {
        return reviewReviewNoteService.save(reviewReviewNote);
    }
    
    @GetMapping
    public Flux<ReviewReviewNote> getAllReviewReviewNotes() {
        return reviewReviewNoteService.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<ReviewReviewNote> getReviewReviewNoteById(@PathVariable UUID id) {
        return reviewReviewNoteService.findById(id);
    }
    
    @GetMapping("/review/{reviewId}")
    public Flux<ReviewReviewNote> getReviewReviewNotesByReviewId(@PathVariable UUID reviewId) {
        return reviewReviewNoteService.findByReviewId(reviewId);
    }
    
    @GetMapping("/review-note/{reviewNoteId}")
    public Flux<ReviewReviewNote> getReviewReviewNotesByReviewNoteId(@PathVariable UUID reviewNoteId) {
        return reviewReviewNoteService.findByReviewNoteId(reviewNoteId);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> deleteReviewReviewNote(@PathVariable UUID id) {
        return reviewReviewNoteService.deleteById(id);
    }
}