package com.otus.homework.billingservice.exceptions;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String message) {
        super(message);
    }
}
