package ink.yowyob.business.book.presentation.dto.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {
    private String username;

    private String password;
}
