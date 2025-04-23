package com.business.book.service;

import com.business.book.entity.ReviewReviewNote;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewReviewNoteService {
    ReviewReviewNote save(ReviewReviewNote reviewReviewNote);
    List<ReviewReviewNote> findAll();
    Optional<ReviewReviewNote> findById(UUID id);
    List<ReviewReviewNote> findByReviewId(UUID reviewId);
    List<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId);
    Optional<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId);
    void deleteById(UUID id);
}