package com.business.book.service.impl;

import com.business.book.entity.ReviewNote;
import com.business.book.repository.ReviewNoteRepository;
import com.business.book.service.ReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewNoteServiceImpl implements ReviewNoteService {

    private final ReviewNoteRepository reviewNoteRepository;

    @Autowired
    public ReviewNoteServiceImpl(ReviewNoteRepository reviewNoteRepository) {
        this.reviewNoteRepository = reviewNoteRepository;
    }

    @Override
    public ReviewNote save(ReviewNote reviewNote) {
        if (reviewNote.getId() == null) {
            reviewNote.setId(UUID.randomUUID());
        }
        return reviewNoteRepository.save(reviewNote);
    }

    @Override
    public List<ReviewNote> findAll() {
        return reviewNoteRepository.findAll();
    }

    @Override
    public Optional<ReviewNote> findById(UUID id) {
        return reviewNoteRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        reviewNoteRepository.deleteById(id);
    }

}