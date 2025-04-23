package com.business.book.repository;

import com.business.book.entity.ReviewVisitor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewVisitorRepository extends CassandraRepository<ReviewVisitor, UUID> {
    List<ReviewVisitor> findByReviewId(UUID reviewId);
    List<ReviewVisitor> findByVisitorId(UUID visitorId);
    Optional<ReviewVisitor> findByReviewIdAndVisitorId(UUID reviewId, UUID visitorId);
}