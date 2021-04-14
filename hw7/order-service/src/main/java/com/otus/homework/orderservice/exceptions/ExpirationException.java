package com.otus.homework.orderservice.exceptions;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String message) {
        super(message);
    }
}
