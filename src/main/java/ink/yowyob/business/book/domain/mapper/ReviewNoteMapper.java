package ink.yowyob.business.book.domain.mapper;

import ink.yowyob.business.book.infrastructure.entity.ReviewNote;
import ink.yowyob.business.book.presentation.dto.reviewNote.CreateReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.ReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.UpdateReviewNoteDto;


public interface ReviewNoteMapper {
    ReviewNote toReviewNote(CreateReviewNoteDto review);

    ReviewNote toReviewNote(UpdateReviewNoteDto review);

    ReviewNote toReviewNote(ReviewNoteDto dto);

    ReviewNoteDto toReviewNoteDto(ReviewNote reviewNote);

    CreateReviewNoteDto toCreateReviewNote(ReviewNote reviewNote);

    UpdateReviewNoteDto toUpdateReviewNoteDto(ReviewNote reviewNote);
}
