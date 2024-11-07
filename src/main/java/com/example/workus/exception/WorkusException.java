package com.example.workus.exception;

public class WorkusException extends RuntimeException {

    public WorkusException(String message) {
        super(message);
    }

    public WorkusException(String message, Throwable caues) {
        super(message,caues);
    }
}
