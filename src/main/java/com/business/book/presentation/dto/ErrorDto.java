package com.business.book.presentation.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ErrorDto {
    public int status;
    public String message;
    public String path;
    public Instant timestamp;
    
    public ErrorDto of(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        return this;
    }
    
    public ErrorDto of (HttpStatus status, String message, String path) {
        this.status = status.value();
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        return this;
    }
}
