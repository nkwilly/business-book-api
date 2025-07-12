package ink.yowyob.business.book.presentation.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateReviewDto {
    private UUID organizationId;

    private String content;
}
