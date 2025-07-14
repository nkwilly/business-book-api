package com.business.book.service.impl;

import com.business.book.entity.ReviewNote;
import com.business.book.repository.ReviewNoteRepository;
import com.business.book.service.ReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ReviewNoteServiceImpl implements ReviewNoteService {

    private final ReviewNoteRepository reviewNoteRepository;

    @Autowired
    public ReviewNoteServiceImpl(ReviewNoteRepository reviewNoteRepository) {
        this.reviewNoteRepository = reviewNoteRepository;
    }

    @Override
    public Mono<ReviewNote> save(ReviewNote reviewNote) {
        reviewNote.setId(UUID.randomUUID());
        return reviewNoteRepository.save(reviewNote);
    }

    @Override
    public Flux<ReviewNote> findAll() {
        return reviewNoteRepository.findAll();
    }

    @Override
    public Mono<ReviewNote> findById(UUID id) {
        return reviewNoteRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        reviewNoteRepository.deleteById(id);
        return Mono.empty();
    }
}