package com.business.book.controller;

import com.business.book.entity.ReviewNote;
import com.business.book.service.ReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ReviewNote> createReviewNote(@RequestBody ReviewNote reviewNote) {
        ReviewNote savedReviewNote = reviewNoteService.save(reviewNote);
        return new ResponseEntity<>(savedReviewNote, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ReviewNote>> getAllReviewNotes() {
        List<ReviewNote> reviewNotes = reviewNoteService.findAll();
        return new ResponseEntity<>(reviewNotes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReviewNote> getReviewNoteById(@PathVariable UUID id) {
        return reviewNoteService.findById(id)
                .map(reviewNote -> new ResponseEntity<>(reviewNote, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<ReviewNote> updateReviewNote(@PathVariable UUID id, @RequestBody ReviewNote reviewNote) {
        return reviewNoteService.findById(id)
                .map(existingReviewNote -> {
                    reviewNote.setId(id);
                    ReviewNote updatedReviewNote = reviewNoteService.save(reviewNote);
                    return new ResponseEntity<>(updatedReviewNote, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Void> deleteReviewNote(@PathVariable UUID id) {
        return reviewNoteService.findById(id)
                .map(reviewNote -> {
                    reviewNoteService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
}