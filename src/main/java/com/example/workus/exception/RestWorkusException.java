package com.example.workus.exception;

public class RestWorkusException extends RuntimeException {

    public RestWorkusException(String message) {
        super(message);
    }

    public RestWorkusException(String message, Throwable caues) {
        super(message,caues);
    }
}
