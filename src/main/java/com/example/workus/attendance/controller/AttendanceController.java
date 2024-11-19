package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.AttendanceDto;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        AttendanceDto attendanceDto = attendanceService.getAttendance(loginUser.getNo());
        model.addAttribute("attendanceDto", attendanceDto);

        System.out.println("loginUser.getNo: " + loginUser.getNo());
        System.out.println("attendanceDto: " + attendanceDto);

        return "attendance/list";
    }
}
