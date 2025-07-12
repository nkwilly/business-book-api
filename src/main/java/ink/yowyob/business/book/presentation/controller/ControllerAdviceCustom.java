package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.exception.AuthoritiesException;
import ink.yowyob.business.book.domain.exception.EntityNotFoundException;
import ink.yowyob.business.book.presentation.dto.ErrorDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerAdviceCustom {
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorDto> handleException(Exception e) {
        return Mono.just(
                ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
        );
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ErrorDto> handleAuthenticationException(AuthenticationException e) {
        return Mono.just(
                ErrorDto.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage())
        );
    }

    @ExceptionHandler(value = {AuthoritiesException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<ErrorDto> handleAuthoritiesException(AuthoritiesException e) {
        return Mono.just(
                ErrorDto.of(HttpStatus.FORBIDDEN.value(), e.getMessage())
        );
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return Mono.just(
                ErrorDto.of(HttpStatus.NOT_FOUND.value(), e.getMessage())
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return Mono.just(
                ErrorDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage())
        );
    }
}