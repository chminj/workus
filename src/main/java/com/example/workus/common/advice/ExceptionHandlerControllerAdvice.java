package com.example.workus.common.advice;

import com.example.workus.common.exception.ApprovalException;
import com.example.workus.common.exception.AttendanceException;
import com.example.workus.common.exception.WorkusException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {//    구체적인 예외 처리기 먼저 작성

    @ExceptionHandler(AttendanceException.class)
    public String handleAttendanceException(AttendanceException ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(ApprovalException.class)
    public String handleApprovalException(ApprovalException ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(WorkusException.class)
    public String handleWorkusException(WorkusException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
