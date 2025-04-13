package com.business.book.service.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    private String username;

    private String password;
}
