package com.otus.homework.orderservice.exceptions;

public class HttpResponseError extends RuntimeException {
    public HttpResponseError(String message) {
        super(message);
    }
}
