package ink.yowyob.business.book.domain.service;

import ink.yowyob.business.book.infrastructure.entity.ReviewNote;

import ink.yowyob.business.book.presentation.dto.reviewNote.CreateReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.UpdateReviewNoteDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewNoteService {
    Mono<ReviewNote> save(CreateReviewNoteDto reviewNote);
    Flux<ReviewNote> findAll();
    Mono<ReviewNote> findById(UUID id);
    Mono<Void> deleteById(UUID id);
    Mono<ReviewNote> update(UUID reviewId, UpdateReviewNoteDto updateEnterpriseDto);
}
