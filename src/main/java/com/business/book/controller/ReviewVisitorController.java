package com.business.book.controller;

import com.business.book.entity.ReviewVisitor;
import com.business.book.service.ReviewVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ReviewVisitor> createReviewVisitor(@RequestBody ReviewVisitor reviewVisitor) {
        ReviewVisitor savedReviewVisitor = reviewVisitorService.save(reviewVisitor);
        return new ResponseEntity<>(savedReviewVisitor, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ReviewVisitor>> getAllReviewVisitors() {
        List<ReviewVisitor> reviewVisitors = reviewVisitorService.findAll();
        return new ResponseEntity<>(reviewVisitors, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReviewVisitor> getReviewVisitorById(@PathVariable UUID id) {
        return reviewVisitorService.findById(id)
                .map(reviewVisitor -> new ResponseEntity<>(reviewVisitor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewVisitor>> getReviewVisitorsByReviewId(@PathVariable UUID reviewId) {
        List<ReviewVisitor> reviewVisitors = reviewVisitorService.findByReviewId(reviewId);
        return new ResponseEntity<>(reviewVisitors, HttpStatus.OK);
    }
    
    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<ReviewVisitor>> getReviewVisitorsByVisitorId(@PathVariable UUID visitorId) {
        List<ReviewVisitor> reviewVisitors = reviewVisitorService.findByVisitorId(visitorId);
        return new ResponseEntity<>(reviewVisitors, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewVisitor(@PathVariable UUID id) {
        reviewVisitorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}