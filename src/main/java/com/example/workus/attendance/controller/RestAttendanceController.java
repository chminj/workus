package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.ApprovalRequestDto;
import com.example.workus.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ajax")
public class RestAttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/approve")
    public ResponseEntity<?> approveRequests(@RequestBody ApprovalRequestDto approvalRequestDto) {
        boolean success = attendanceService.approveRequests(approvalRequestDto);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
