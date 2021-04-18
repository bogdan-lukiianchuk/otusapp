package com.otus.homework.userserviceauth.exceptions;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String message) {
        super(message);
    }
}
