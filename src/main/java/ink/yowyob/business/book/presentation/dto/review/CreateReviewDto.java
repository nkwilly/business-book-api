package ink.yowyob.business.book.presentation.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateReviewDto {
    private UUID organizationId;

    private String content;
}
