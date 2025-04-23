package com.business.book.repository;

import com.business.book.entity.ReviewReviewNote;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewReviewNoteRepository extends CassandraRepository<ReviewReviewNote, UUID> {
    List<ReviewReviewNote> findByReviewId(UUID reviewId);
    List<ReviewReviewNote> findByReviewNoteId(UUID reviewNoteId);
    Optional<ReviewReviewNote> findByReviewIdAndReviewNoteId(UUID reviewId, UUID reviewNoteId);
}