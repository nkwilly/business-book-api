package ink.yowyob.business.book.domain.mapper;

import ink.yowyob.business.book.infrastructure.entity.Review;
import ink.yowyob.business.book.presentation.dto.review.CreateReviewDto;
import ink.yowyob.business.book.presentation.dto.review.ReviewDto;
import ink.yowyob.business.book.presentation.dto.review.UpdateReviewDto;

public interface ReviewMapper {
    Review toReview(CreateReviewDto createReviewDto);

    Review toReview(ReviewDto reviewDto);

    Review toReview(UpdateReviewDto updateReviewNoteDto);

    UpdateReviewDto toUpdateReviewDto(Review review);

    ReviewDto toReviewDto(Review review);

    CreateReviewDto toCreateReviewDto(Review review);
}
