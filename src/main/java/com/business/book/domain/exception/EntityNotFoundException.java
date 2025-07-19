package com.business.book.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
