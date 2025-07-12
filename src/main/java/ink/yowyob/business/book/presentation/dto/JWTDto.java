package ink.yowyob.business.book.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
    public class JWTDto {
        private String token;

        private String type = "Bearer";

        private String username;

        private List<String> roles;
    }
