package com.otus.homework.warehouseservice.exceptions;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message, Throwable t) {
        super(message, t);
    }
}
