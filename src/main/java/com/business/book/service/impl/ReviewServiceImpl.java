package com.business.book.service.impl;

import com.business.book.entity.Review;
import com.business.book.repository.ReviewRepository;
import com.business.book.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    @Override
    public Mono<Review> save(Review review) {
        if (review.getId() == null) {
            review.setId(UUID.randomUUID());
        }
        return reviewRepository.save(review);
    }
    
    @Override
    public Flux<Review> findAll() {
        return reviewRepository.findAll();
    }
    
    @Override
    public Mono<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }
    
    @Override
    public Mono<Void> deleteById(UUID id) {
        reviewRepository.deleteById(id);
        return Mono.empty();
    }
   
}