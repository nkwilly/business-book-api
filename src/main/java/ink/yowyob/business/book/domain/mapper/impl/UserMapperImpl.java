package ink.yowyob.business.book.domain.mapper.impl;

import ink.yowyob.business.book.domain.mapper.UserMapper;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.presentation.dto.user.UserDTO;
import ink.yowyob.business.book.utils.GenerateUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setTel(userDTO.getTel());
        return user;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setTel(user.getTel());
        return userDTO;
    }

    @Override
    public User toUser(RegisterDto dto) {
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
