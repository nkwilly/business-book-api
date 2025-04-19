package com.business.book.service.payload.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class RegisterRequest {
    @NotBlank
    private String username;

     @NotBlank
     @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String tel;
}
