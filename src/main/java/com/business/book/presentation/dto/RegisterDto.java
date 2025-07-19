package com.business.book.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class RegisterDto {
    @NotBlank
    private String username;

     @NotBlank
     @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String tel;
}
