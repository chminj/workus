package com.example.workus.common.advice;

import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.common.exception.RestAttendanceException;
import com.example.workus.common.exception.RestWorkusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerRestControllerAdvice {

    @ExceptionHandler(RestWorkusException.class)
    public ResponseEntity<RestResponseDto<Void>> handlerWorkusException(RestWorkusException e) {
        String msg = e.getMessage();
        return ResponseEntity.internalServerError().body(RestResponseDto.fail(msg));
    }

    @ExceptionHandler(RestAttendanceException.class)
    public ResponseEntity<RestResponseDto<String>> handleRestAttendanceException(RestAttendanceException ex) {
        RestResponseDto<String> response = RestResponseDto.fail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
