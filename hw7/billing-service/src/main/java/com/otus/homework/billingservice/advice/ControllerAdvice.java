package com.otus.homework.billingservice.advice;

import com.otus.homework.billingservice.exceptions.ExpirationException;
import com.otus.homework.billingservice.exceptions.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public String handle(Exception e) {
        log.error("handler catch", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class})
    public String handleValidationException(NoSuchElementException e) {
        log.error("handler catch", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String invalidMethodArgumentExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("handler catch", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public String invalidTokenExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("handler catch", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpirationException.class)
    public String expirationExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("handler catch", e);
        return e.getMessage();
    }
}
