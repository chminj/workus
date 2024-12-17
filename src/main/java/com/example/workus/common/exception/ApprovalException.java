package com.example.workus.common.exception;

public class ApprovalException extends WorkusException{

    public ApprovalException(String errorCode, String message) {
        super(errorCode, message);
    }
}
