package com.business.book.repository;

import com.business.book.entity.Review;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends CassandraRepository<Review, UUID> {
    List<Review> findByOrganizationId(UUID organizationId);
}