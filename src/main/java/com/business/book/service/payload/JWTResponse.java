package com.business.book.service.payload;

import com.business.book.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class JWTResponse {
    private String token;

    private String type = "Bearer";

    private String username;

    private List<String> roles;
}
