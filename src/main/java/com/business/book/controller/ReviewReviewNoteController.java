package com.business.book.controller;

import com.business.book.entity.ReviewReviewNote;
import com.business.book.service.ReviewReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    public ResponseEntity<ReviewReviewNote> createReviewReviewNote(@RequestBody ReviewReviewNote reviewReviewNote) {
        ReviewReviewNote savedReviewReviewNote = reviewReviewNoteService.save(reviewReviewNote);
        return new ResponseEntity<>(savedReviewReviewNote, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ReviewReviewNote>> getAllReviewReviewNotes() {
        List<ReviewReviewNote> reviewReviewNotes = reviewReviewNoteService.findAll();
        return new ResponseEntity<>(reviewReviewNotes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReviewReviewNote> getReviewReviewNoteById(@PathVariable UUID id) {
        return reviewReviewNoteService.findById(id)
                .map(reviewReviewNote -> new ResponseEntity<>(reviewReviewNote, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewReviewNote>> getReviewReviewNotesByReviewId(@PathVariable UUID reviewId) {
        List<ReviewReviewNote> reviewReviewNotes = reviewReviewNoteService.findByReviewId(reviewId);
        return new ResponseEntity<>(reviewReviewNotes, HttpStatus.OK);
    }
    
    @GetMapping("/review-note/{reviewNoteId}")
    public ResponseEntity<List<ReviewReviewNote>> getReviewReviewNotesByReviewNoteId(@PathVariable UUID reviewNoteId) {
        List<ReviewReviewNote> reviewReviewNotes = reviewReviewNoteService.findByReviewNoteId(reviewNoteId);
        return new ResponseEntity<>(reviewReviewNotes, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewReviewNote(@PathVariable UUID id) {
        reviewReviewNoteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}