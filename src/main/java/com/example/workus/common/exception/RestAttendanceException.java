package com.example.workus.common.exception;

public class RestAttendanceException extends RestWorkusException {
    public RestAttendanceException(String message) {
        super(message);
    }

    public RestAttendanceException(String message, Throwable caues) {
        super(message, caues);
    }
}
