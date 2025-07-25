package com.business.book.presentation.dto;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class UserDTO {
    private UUID id;

    private String username;

    private String tel;
}
