package com.business.book.service.impl;

import com.business.book.entity.ReviewVisitor;
import com.business.book.repository.ReviewVisitorRepository;
import com.business.book.service.ReviewVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ReviewVisitor> save(ReviewVisitor reviewVisitor) {
        return reviewVisitorRepository.save(reviewVisitor);
    }
    
    @Override
    public Flux<ReviewVisitor> findAll() {
        return reviewVisitorRepository.findAll();
    }
    
    @Override
    public Mono<ReviewVisitor> findById(UUID id) {
        return reviewVisitorRepository.findById(id);
    }
    
    @Override
    public Flux<ReviewVisitor> findByReviewId(UUID reviewId) {
        return reviewVisitorRepository.findByReviewId(reviewId);
    }
    
    @Override
    public Flux<ReviewVisitor> findByVisitorId(UUID visitorId) {
        return reviewVisitorRepository.findByVisitorId(visitorId);
    }
    
    @Override
    public Mono<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId) {
        return reviewVisitorRepository.findByReviewIdAndVisitorId(reviewId, visitorId);
    }
    
    @Override
    public Mono<Void> deleteById(UUID id) {
        reviewVisitorRepository.deleteById(id);
        return Mono.empty();
    }
}