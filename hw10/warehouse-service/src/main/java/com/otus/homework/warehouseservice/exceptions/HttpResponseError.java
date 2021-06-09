package com.otus.homework.warehouseservice.exceptions;

public class HttpResponseError extends RuntimeException {
    public HttpResponseError(String message) {
        super(message);
    }
}
