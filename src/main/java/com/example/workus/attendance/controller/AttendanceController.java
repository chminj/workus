package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.ApprovalForm;
import com.example.workus.attendance.dto.ApprovalUserDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/getApproval")
    public String getApproval(ApprovalForm form, @AuthenticationPrincipal LoginUser loginUser) {
        ApprovalForm apvForm = new ApprovalForm();
        apvForm.setNo(form.getNo());
        apvForm.setReason(form.getReason());
        apvForm.setFromDate(form.getFromDate());
        apvForm.setToDate(form.getToDate());
        apvForm.setCategoryNo(form.getCategoryNo());
        apvForm.setUserNo(loginUser.getNo());
        apvForm.setTime(form.getTime());
        List<ApprovalUserDto> users = new ArrayList<>();
        String[] apvs = form.getApv().split(",");
        String[] refs = form.getRef().split(",");

        int index = 0;
        for (String value : apvs) {
            Long intValue = Long.valueOf(value);

            ApprovalUserDto userDto = ApprovalUserDto.builder()
                    .status('1')
                    .userNo(intValue)
                    .sequence(index++)
                    .formNo(form.getNo())
                    .build();
            users.add(userDto);
        }

        index = 0;
        for (String value : refs) {
            Long intValue = Long.valueOf(value);
            ApprovalUserDto userDto = ApprovalUserDto.builder()
                    .status('2')
                    .userNo(intValue)
                    .sequence(index++)
                    .formNo(form.getNo())
                    .build();
            users.add(userDto);
        }

        attendanceService.insertApprovalForm(apvForm, users);

        return "redirect:/attendance/list";
    }

    @GetMapping("/myApvList")
    public String myApvList(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        return "attendance/myApvList";
    }
}
