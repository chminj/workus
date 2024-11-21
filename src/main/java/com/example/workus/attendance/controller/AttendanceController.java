package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.AttendanceCategoryDto;
import com.example.workus.attendance.dto.AttendanceDto;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.security.LoginUser;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        AttendanceDto attendanceDto = attendanceService.getAttendance(loginUser.getNo());
        model.addAttribute("attendanceDto", attendanceDto);

        return "attendance/list";
    }

    @GetMapping("/atdFormInUser")
    @ResponseBody
    public List<User> usersInAtdForm() {
        return (List<User>) userService.getAllUsers();
    }

    @GetMapping("/atdFormInCtgr")
    @ResponseBody
    public List<AttendanceCategoryDto> categoriesInAtdForm() {
        return (List<AttendanceCategoryDto>) attendanceService.getCategories();
    }
}
