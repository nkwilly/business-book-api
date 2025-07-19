package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.ReviewNote;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewNoteRepository extends ReactiveCassandraRepository<ReviewNote, UUID> {
}