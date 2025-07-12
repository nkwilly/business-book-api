package ink.yowyob.business.book.domain.service.impl;

import ink.yowyob.business.book.domain.mapper.ReviewMapper;
import ink.yowyob.business.book.infrastructure.entity.Review;
import ink.yowyob.business.book.infrastructure.repository.ReviewRepository;
import ink.yowyob.business.book.domain.service.ReviewService;
import ink.yowyob.business.book.presentation.dto.review.CreateReviewDto;
import ink.yowyob.business.book.presentation.dto.review.UpdateReviewDto;
import ink.yowyob.business.book.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public Mono<Review> save(CreateReviewDto dto) {
        Review review = reviewMapper.toReview(dto);
        review.setId(GenerateUtils.generateId());
        return reviewRepository.save(review);
    }

    @Override
    public Flux<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Mono<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Mono<Review> update(UUID id, UpdateReviewDto review) {
        return reviewRepository.findById(id)
                .flatMap(existingReview -> {
                    Review updatedReview = reviewMapper.toReview(review);
                    updatedReview.setId(existingReview.getId());
                    return reviewRepository.save(updatedReview);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Review not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return reviewRepository.deleteById(id);
    }
}