package com.otus.homework.productservice.exceptions;

public class HttpResponseError extends RuntimeException {
    public HttpResponseError(String message) {
        super(message);
    }
}
