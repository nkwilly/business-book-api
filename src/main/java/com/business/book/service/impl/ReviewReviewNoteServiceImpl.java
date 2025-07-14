package com.business.book.service.impl;

import com.business.book.entity.ReviewReviewNote;
import com.business.book.repository.ReviewReviewNoteRepository;
import com.business.book.service.ReviewReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ReviewReviewNoteServiceImpl implements ReviewReviewNoteService {
    
    private final ReviewReviewNoteRepository reviewReviewNoteRepository;
    
    @Autowired
    public ReviewReviewNoteServiceImpl(ReviewReviewNoteRepository reviewReviewNoteRepository) {
        this.reviewReviewNoteRepository = reviewReviewNoteRepository;
    }
    
    @Override
    public Mono<ReviewReviewNote> save(ReviewReviewNote reviewReviewNote) {
        return reviewReviewNoteRepository.save(reviewReviewNote);
    }
    
    @Override
    public Flux<ReviewReviewNote> findAll() {
        return reviewReviewNoteRepository.findAll();
    }
    
    @Override
    public Mono<ReviewReviewNote> findById(UUID id) {
        return reviewReviewNoteRepository.findById(id);
    }
    
    @Override
    public Flux<ReviewReviewNote> findByReviewId(UUID reviewId) {
        return reviewReviewNoteRepository.findByReviewId(reviewId);
    }
    
    @Override
    public Flux<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId) {
        return reviewReviewNoteRepository.findByReviewNoteId(reviewNoteId);
    }
    
    @Override
    public Mono<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId) {
        return reviewReviewNoteRepository.findByReviewIdAndReviewNoteId(reviewId, reviewNoteId);
    }
    
    @Override
    public Mono<Void> deleteById(UUID id) {
        reviewReviewNoteRepository.deleteById(id);
        return Mono.empty();
    }
}