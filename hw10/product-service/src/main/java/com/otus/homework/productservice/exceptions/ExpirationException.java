package com.otus.homework.productservice.exceptions;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String message) {
        super(message);
    }
}
