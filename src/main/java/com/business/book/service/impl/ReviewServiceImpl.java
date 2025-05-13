package com.business.book.service.impl;

import com.business.book.entity.Review;
import com.business.book.repository.ReviewRepository;
import com.business.book.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(UUID.randomUUID());
        }
        return reviewRepository.save(review);
    }
    
    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    
    @Override
    public Optional<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }
    
    @Override
    public void deleteById(UUID id) {
        reviewRepository.deleteById(id);
    }
   
}