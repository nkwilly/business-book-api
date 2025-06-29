package com.business.book.service.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

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
