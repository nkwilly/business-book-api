package ink.yowyob.business.book.domain.mapper.impl;

import ink.yowyob.business.book.domain.mapper.ReviewMapper;
import ink.yowyob.business.book.infrastructure.entity.Review;
import ink.yowyob.business.book.presentation.dto.review.CreateReviewDto;
import ink.yowyob.business.book.presentation.dto.review.ReviewDto;
import ink.yowyob.business.book.presentation.dto.review.UpdateReviewDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toReview(CreateReviewDto createReviewDto) {
        if (createReviewDto == null) {
            return null;
        }

        Review review = Review.builder()
                .organizationId(createReviewDto.getOrganizationId())
                .content(createReviewDto.getContent())
                .build();
        return review;
    }

    @Override
    public Review toReview(ReviewDto reviewDto) {
        if (reviewDto == null) {
            return null;
        }

        Review review = Review.builder()
                .organizationId(reviewDto.getOrganizationId())
                .content(reviewDto.getContent())
                .build();
        return review;
    }

    @Override
    public Review toReview(UpdateReviewDto updateReviewDto) {
        if (updateReviewDto == null) {
            return null;
        }

        Review review = Review.builder()
                .organizationId(updateReviewDto.getOrganizationId())
                .content(updateReviewDto.getContent())
                .build();
        return review;
    }

    @Override
    public UpdateReviewDto toUpdateReviewDto(Review review) {
        if (review == null) {
            return null;
        }

        UpdateReviewDto updateReviewDto = new UpdateReviewDto();
        updateReviewDto.setOrganizationId(review.getOrganizationId());
        updateReviewDto.setContent(review.getContent());
        return updateReviewDto;
    }

    @Override
    public ReviewDto toReviewDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDto reviewDto = ReviewDto.builder()
                .organizationId(review.getOrganizationId())
                .content(review.getContent())
                .build();
        return reviewDto;
    }

    @Override
    public CreateReviewDto toCreateReviewDto(Review review) {
        if (review == null) {
            return null;
        }

        CreateReviewDto createReviewDto = new CreateReviewDto();
        createReviewDto.setOrganizationId(review.getOrganizationId());
        createReviewDto.setContent(review.getContent());
        return createReviewDto;
    }
}
