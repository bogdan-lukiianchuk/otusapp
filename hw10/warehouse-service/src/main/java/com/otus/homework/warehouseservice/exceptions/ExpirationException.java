package com.otus.homework.warehouseservice.exceptions;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String message) {
        super(message);
    }
}
