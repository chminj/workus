package com.example.workus.common.exception;

public class WorkusException extends RuntimeException {

    private String errorCode;

    public WorkusException(String message) {
        super(message);
    }

    public WorkusException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WorkusException(String errorCode, String message, Throwable caues) {
        super(message,caues);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
