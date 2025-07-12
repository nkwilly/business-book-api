package ink.yowyob.business.book.domain.mapper.impl;

import ink.yowyob.business.book.domain.mapper.ReviewNoteMapper;
import ink.yowyob.business.book.infrastructure.entity.ReviewNote;
import ink.yowyob.business.book.presentation.dto.reviewNote.CreateReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.ReviewNoteDto;
import ink.yowyob.business.book.presentation.dto.reviewNote.UpdateReviewNoteDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewNoteMapperImpl implements ReviewNoteMapper {

    @Override
    public ReviewNote toReviewNote(CreateReviewNoteDto createReviewNoteDto) {
        if (createReviewNoteDto == null) {
            return null;
        }

        ReviewNote reviewNote = ReviewNote.builder()
                .organizationId(createReviewNoteDto.getOrganizationId())
                .note(createReviewNoteDto.getNote())
                .build();
        return reviewNote;
    }

    @Override
    public ReviewNote toReviewNote(UpdateReviewNoteDto updateReviewNoteDto) {
        if (updateReviewNoteDto == null) {
            return null;
        }

        ReviewNote reviewNote = ReviewNote.builder()
                .organizationId(updateReviewNoteDto.getOrganizationId())
                .note(updateReviewNoteDto.getNote())
                .build();
        return reviewNote;
    }

    @Override
    public ReviewNote toReviewNote(ReviewNoteDto dto) {
        if (dto == null) {
            return null;
        }

        ReviewNote reviewNote = ReviewNote.builder()
                .organizationId(dto.getOrganizationId())
                .note(dto.getNote())
                .build();
        return reviewNote;
    }

    @Override
    public ReviewNoteDto toReviewNoteDto(ReviewNote reviewNote) {
        if (reviewNote == null) {
            return null;
        }

        ReviewNoteDto reviewNoteDto = new ReviewNoteDto();
        reviewNoteDto.setOrganizationId(reviewNote.getOrganizationId());
        reviewNoteDto.setNote(reviewNote.getNote());
        return reviewNoteDto;
    }

    @Override
    public CreateReviewNoteDto toCreateReviewNote(ReviewNote reviewNote) {
        if (reviewNote == null) {
            return null;
        }

        CreateReviewNoteDto createReviewNoteDto = new CreateReviewNoteDto();
        createReviewNoteDto.setOrganizationId(reviewNote.getOrganizationId());
        createReviewNoteDto.setNote(reviewNote.getNote());
        return createReviewNoteDto;
    }

    @Override
    public UpdateReviewNoteDto toUpdateReviewNoteDto(ReviewNote reviewNote) {
        if (reviewNote == null) {
            return null;
        }

        UpdateReviewNoteDto updateReviewNoteDto = new UpdateReviewNoteDto();
        updateReviewNoteDto.setOrganizationId(reviewNote.getOrganizationId());
        updateReviewNoteDto.setNote(reviewNote.getNote());
        return updateReviewNoteDto;
    }
}
