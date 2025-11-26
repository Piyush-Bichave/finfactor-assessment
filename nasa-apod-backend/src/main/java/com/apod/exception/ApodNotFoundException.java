package com.apod.exception;

public class ApodNotFoundException extends RuntimeException {
    public ApodNotFoundException(String message) {
        super(message);
    }
}
