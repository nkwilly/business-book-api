package com.business.book.service.payload.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    private String username;

    private String password;
}
