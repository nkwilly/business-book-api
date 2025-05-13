package com.business.book.service;

import com.business.book.entity.ReviewVisitor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewVisitorService {
    ReviewVisitor save(ReviewVisitor reviewVisitor);
    List<ReviewVisitor> findAll();
    Optional<ReviewVisitor> findById(UUID id);
    List<ReviewVisitor> findByReviewId(UUID reviewId);
    List<ReviewVisitor> findByVisitorId(UUID visitorId);
    Optional<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId);
    void deleteById(UUID id);
}