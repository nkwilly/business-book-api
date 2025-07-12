package ink.yowyob.business.book.presentation.dto.review;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@Builder
public class ReviewDto {
    private UUID organizationId;

    private String content;
}