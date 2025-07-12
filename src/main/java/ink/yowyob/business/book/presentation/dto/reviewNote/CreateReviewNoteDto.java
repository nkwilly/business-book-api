package ink.yowyob.business.book.presentation.dto.reviewNote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewNoteDto {
    private UUID organizationId;

    private Integer note;
}