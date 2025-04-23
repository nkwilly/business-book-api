package com.business.book.service.impl;

import com.business.book.entity.ReviewVisitor;
import com.business.book.repository.ReviewVisitorRepository;
import com.business.book.service.ReviewVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewVisitorServiceImpl implements ReviewVisitorService {
    
    private final ReviewVisitorRepository reviewVisitorRepository;
    
    @Autowired
    public ReviewVisitorServiceImpl(ReviewVisitorRepository reviewVisitorRepository) {
        this.reviewVisitorRepository = reviewVisitorRepository;
    }
    
    @Override
    public ReviewVisitor save(ReviewVisitor reviewVisitor) {
        return reviewVisitorRepository.save(reviewVisitor);
    }
    
    @Override
    public List<ReviewVisitor> findAll() {
        return reviewVisitorRepository.findAll();
    }
    
    @Override
    public Optional<ReviewVisitor> findById(UUID id) {
        return reviewVisitorRepository.findById(id);
    }
    
    @Override
    public List<ReviewVisitor> findByReviewId(UUID reviewId) {
        return reviewVisitorRepository.findByReviewId(reviewId);
    }
    
    @Override
    public List<ReviewVisitor> findByVisitorId(UUID visitorId) {
        return reviewVisitorRepository.findByVisitorId(visitorId);
    }
    
    @Override
    public Optional<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId) {
        return reviewVisitorRepository.findByReviewIdAndVisitorId(reviewId, visitorId);
    }
    
    @Override
    public void deleteById(UUID id) {
        reviewVisitorRepository.deleteById(id);
    }
}