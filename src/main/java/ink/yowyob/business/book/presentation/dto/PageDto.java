package ink.yowyob.business.book.presentation.dto;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    int totalResult;
    Flux<Enterprise> enterprises;
}
