package com.example.workus.common.advice;

import com.example.workus.common.exception.AttendanceException;
import com.example.workus.common.exception.WorkusException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }

    @ExceptionHandler(WorkusException.class)
    public String handleWorkusException(WorkusException e, Model model) {
        model.addAttribute("msg", e.getMessage());
        return "error"; // msg의 이름으로 에러메시지를 담을 수 있는 에러 페이지
    }

    @ExceptionHandler(AttendanceException.class)
    public String handleAttendanceException(AttendanceException ex, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value()); // 또는 HttpStatus.INTERNAL_SERVER_ERROR.value()
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
