package ink.yowyob.business.book.domain.service.impl;

import ink.yowyob.business.book.domain.mapper.ReviewNoteMapper;
import ink.yowyob.business.book.infrastructure.entity.ReviewNote;
import ink.yowyob.business.book.infrastructure.repository.ReviewNoteRepository;
import ink.yowyob.business.book.domain.service.ReviewNoteService;
import ink.yowyob.business.book.presentation.dto.reviewNote.CreateReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.UpdateReviewNoteDto;
import ink.yowyob.business.book.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ReviewNoteServiceImpl implements ReviewNoteService {

    private final ReviewNoteRepository reviewNoteRepository;
    private final ReviewNoteMapper reviewNoteMapper;

    @Autowired
    public ReviewNoteServiceImpl(ReviewNoteRepository reviewNoteRepository, ReviewNoteMapper reviewNoteMapper) {
        this.reviewNoteRepository = reviewNoteRepository;
        this.reviewNoteMapper = reviewNoteMapper;
    }

    @Override
    public Mono<ReviewNote> save(CreateReviewNoteDto dto) {
        ReviewNote reviewNote = reviewNoteMapper.toReviewNote(dto);
        reviewNote.setId(GenerateUtils.generateId());
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
        return reviewNoteRepository.deleteById(id);
    }

    @Override
    public Mono<ReviewNote> update(UUID reviewId, UpdateReviewNoteDto dto) {
        if (Boolean.TRUE.equals(reviewNoteRepository.existsById(reviewId))) {
            ReviewNote review = reviewNoteMapper.toReviewNote(dto);
            review.setId(reviewId);
            return reviewNoteRepository.findById(reviewId)
                    .flatMap(existingReviewNote -> {
                        existingReviewNote.setOrganizationId(dto.getOrganizationId());
                        existingReviewNote.setNote(dto.getNote());
                        return reviewNoteRepository.save(existingReviewNote);
                    });
        }
        else
            return Mono.error(new RuntimeException("Review note not found"));
    }

}