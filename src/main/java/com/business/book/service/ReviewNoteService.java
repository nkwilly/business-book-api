package com.business.book.service;

import com.business.book.entity.ReviewNote;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewNoteService {
    ReviewNote save(ReviewNote reviewNote);
    List<ReviewNote> findAll();
    Optional<ReviewNote> findById(UUID id);
    void deleteById(UUID id);
}