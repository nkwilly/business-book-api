package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.mapper.ReviewMapper;
import ink.yowyob.business.book.infrastructure.entity.Review;
import ink.yowyob.business.book.infrastructure.repository.ReviewRepository;
import ink.yowyob.business.book.domain.service.ReviewService;
import ink.yowyob.business.book.presentation.dto.*;
import ink.yowyob.business.book.presentation.dto.review.CreateReviewDto;
import ink.yowyob.business.book.presentation.dto.review.ReviewDto;
import ink.yowyob.business.book.presentation.dto.review.UpdateReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<Review>> createReview(@RequestBody CreateReviewDto createReviewRequest) {
        return reviewService.save(createReviewRequest)
                .map(savedReview -> SuccessDto.of(201, "Review created successfully", "/api/reviews", savedReview));
    }
    
    @GetMapping
    public Flux<Review> getAllReviews() {
        return reviewService.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<SuccessDto<Review>> getReviewById(@PathVariable UUID id) {
        return reviewService.findById(id)
                .map(review -> SuccessDto.of(200, "Review found successfully", "/api/reviews/" + id, review));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<ReviewDto>> updateReview(@PathVariable UUID id, @RequestBody UpdateReviewDto review) {
        return reviewService.update(id, review)
                .map(reviewMapper::toReviewDto)
                .map(updatedReview -> SuccessDto.of(200, "Review updated successfully", "/api/reviews/" + id, updatedReview));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public Mono<SuccessDto<Object>> deleteReview(@PathVariable UUID id) {
        return reviewService.findById(id)
                .flatMap(review -> reviewService.deleteById(id)
                        .then(Mono.just(SuccessDto.of(204, "Review deleted successfully", "/api/reviews/" + id))))
                .defaultIfEmpty(SuccessDto.of(404, "Review not found", "/api/reviews/" + id));
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<ReviewDto> getReviewsByOrganizationId(@PathVariable UUID organizationId) {
        return reviewRepository.findByOrganizationId(organizationId)
                .map(reviewMapper::toReviewDto);
    }
}
