package com.business.book.repository;

import com.business.book.entity.ReviewNote;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewNoteRepository extends CassandraRepository<ReviewNote, UUID> {
}