package com.otus.homework.productservice.exceptions;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message, Throwable t) {
        super(message, t);
    }
}
