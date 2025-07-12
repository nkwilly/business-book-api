package ink.yowyob.business.book.presentation.dto.user;

import ink.yowyob.business.book.infrastructure.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialUser {
    private UUID id;
    private String username;
    private String email;
    private String tel;
    private String password;

    public SpecialUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.tel = user.getTel();
        this.password = user.getPassword();
    }
}
