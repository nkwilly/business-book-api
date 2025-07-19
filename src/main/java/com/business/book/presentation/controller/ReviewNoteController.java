package com.business.book.presentation.controller;

import com.business.book.infrastructure.entity.ReviewNote;
import com.business.book.domain.service.ReviewNoteService;
import com.business.book.presentation.dto.CreateReviewNoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/review-notes")
public class ReviewNoteController {
    
    private final ReviewNoteService reviewNoteService;
    
    @Autowired
    public ReviewNoteController(ReviewNoteService reviewNoteService) {
        this.reviewNoteService = reviewNoteService;
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<ReviewNote> createReviewNote(@RequestBody CreateReviewNoteDto createReviewNoteRequest) {
        ReviewNote reviewNote = ReviewNote.builder()
                .organizationId(createReviewNoteRequest.getOrganizationId())
                .note(createReviewNoteRequest.getNote())
                .build();
        return reviewNoteService.save(reviewNote);
    }
    
    @GetMapping
    public Flux<ReviewNote> getAllReviewNotes() {
       return reviewNoteService.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<ReviewNote> getReviewNoteById(@PathVariable UUID id) {
        return reviewNoteService.findById(id);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<ReviewNote> updateReviewNote(@PathVariable UUID id, @RequestBody ReviewNote reviewNote) {
        return reviewNoteService.findById(id)
                .flatMap(existingReviewNote -> {
                    reviewNote.setId(id);
                    return reviewNoteService.save(reviewNote);
                });
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<Void> deleteReviewNote(@PathVariable UUID id) {
        return reviewNoteService.findById(id)
                .flatMap(reviewNote -> {
                    return reviewNoteService.deleteById(id);
                });
    }
}