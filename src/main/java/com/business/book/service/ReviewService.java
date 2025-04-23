package com.business.book.service;

import com.business.book.entity.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {
    Review save(Review review);
    List<Review> findAll();
    Optional<Review> findById(UUID id);
    void deleteById(UUID id);
}