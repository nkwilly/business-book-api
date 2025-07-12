package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.mapper.ReviewNoteMapper;
import ink.yowyob.business.book.domain.service.ReviewNoteService;
import ink.yowyob.business.book.presentation.dto.reviewNote.CreateReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.ReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.SuccessDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.UpdateReviewNoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/review-notes")
@RequiredArgsConstructor
public class ReviewNoteController {
    
    private final ReviewNoteService reviewNoteService;
    private final ReviewNoteMapper reviewNoteMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<ReviewNoteDto>> createReviewNote(@RequestBody CreateReviewNoteDto createReviewNoteDto) {
        return reviewNoteService.save(createReviewNoteDto)
                .map(reviewNoteMapper::toReviewNoteDto)
                .map(savedReviewNote -> SuccessDto.of(201, "Review note created successfully", "/api/review-notes", savedReviewNote));
    }
    
    @GetMapping
    public Flux<ReviewNoteDto> getAllReviewNotes() {
        return reviewNoteService.findAll()
                .map(reviewNoteMapper::toReviewNoteDto);
    }
    
    @GetMapping("/{id}")
    public Mono<SuccessDto<ReviewNoteDto>> getReviewNoteById(@PathVariable UUID id) {
        return reviewNoteService.findById(id)
                .map(reviewNoteMapper::toReviewNoteDto)
                .map(reviewNote -> SuccessDto.of(200, "Review note found successfully", "/api/review-notes/" + id, reviewNote));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<ReviewNoteDto>> updateReviewNote(@PathVariable UUID id, @RequestBody UpdateReviewNoteDto reviewNote) {
        return reviewNoteService.update(id, reviewNote)
                .map(reviewNoteMapper::toReviewNoteDto)
                .map(updatedReviewNote -> SuccessDto.of(200, "Review note updated successfully", "/api/review-notes/" + id, updatedReviewNote))
                .defaultIfEmpty(SuccessDto.of(404, "Review note not found", "/api/review-notes/" + id));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<Object>> deleteReviewNote(@PathVariable UUID id) {
        return reviewNoteService.findById(id)
                .flatMap(reviewNote -> reviewNoteService.deleteById(id)
                        .then(Mono.just(SuccessDto.of(204, "Review note deleted successfully", "/api/review-notes/" + id))))
                .defaultIfEmpty(SuccessDto.of(404, "Review note not found", "/api/review-notes/" + id));
    }
}
