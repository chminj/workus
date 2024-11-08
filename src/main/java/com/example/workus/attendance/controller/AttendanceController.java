package com.example.workus.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    //@Autowired

    @GetMapping("/list")
    public String list() {
        return "attendance/list";
    }
}
