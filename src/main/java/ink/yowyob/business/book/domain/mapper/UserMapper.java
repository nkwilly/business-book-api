package ink.yowyob.business.book.domain.mapper;

import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.presentation.dto.user.UserDTO;
import ink.yowyob.business.book.utils.GenerateUtils;

import java.time.Instant;

public interface UserMapper {

    User toUser(UserDTO userDTO);

    UserDTO toUserDTO(User user);

    default User toUser(RegisterDto dto)  {
        if (dto == null) {
            System.out.println("\n\n\nRegisterDto is null\n\n\n");
            return null;
        }
        User user = new User();
        user.setId(GenerateUtils.generateId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setTel(dto.getTel());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setCreatedBy(user.getId());
        user.setLastModifiedBy(user.getId());
        return user;
    }
}
