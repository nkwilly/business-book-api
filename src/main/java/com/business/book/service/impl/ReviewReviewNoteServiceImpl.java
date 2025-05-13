package com.business.book.service.impl;

import com.business.book.entity.ReviewReviewNote;
import com.business.book.repository.ReviewReviewNoteRepository;
import com.business.book.service.ReviewReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewReviewNoteServiceImpl implements ReviewReviewNoteService {
    
    private final ReviewReviewNoteRepository reviewReviewNoteRepository;
    
    @Autowired
    public ReviewReviewNoteServiceImpl(ReviewReviewNoteRepository reviewReviewNoteRepository) {
        this.reviewReviewNoteRepository = reviewReviewNoteRepository;
    }
    
    @Override
    public ReviewReviewNote save(ReviewReviewNote reviewReviewNote) {
        return reviewReviewNoteRepository.save(reviewReviewNote);
    }
    
    @Override
    public List<ReviewReviewNote> findAll() {
        return reviewReviewNoteRepository.findAll();
    }
    
    @Override
    public Optional<ReviewReviewNote> findById(UUID id) {
        return reviewReviewNoteRepository.findById(id);
    }
    
    @Override
    public List<ReviewReviewNote> findByReviewId(UUID reviewId) {
        return reviewReviewNoteRepository.findByReviewId(reviewId);
    }
    
    @Override
    public List<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId) {
        return reviewReviewNoteRepository.findByReviewNoteId(reviewNoteId);
    }
    
    @Override
    public Optional<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId) {
        return reviewReviewNoteRepository.findByReviewIdAndReviewNoteId(reviewId, reviewNoteId);
    }
    
    @Override
    public void deleteById(UUID id) {
        reviewReviewNoteRepository.deleteById(id);
    }
}