package com.example.workus.approval.controller;

import com.example.workus.approval.service.ApprovalService;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.security.LoginUser;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    private final ApprovalService approvalService;
    private final UserService userService;

    @Autowired
    public ApprovalController(ApprovalService approvalService, UserService userService) {
        this.approvalService = approvalService;
        this.userService = userService;
    }


    @GetMapping("/form-list")
    public String list(@AuthenticationPrincipal LoginUser loginUser, Model model) {

        List<ApprovalCategory> categories = approvalService.getCategories();
        model.addAttribute("categories", categories);

        Long userNo = loginUser.getNo();
        User leader = userService.getUserByUserNo(userNo);
        long deptNo = leader.getDeptNo();
        approvalService.getLeader(deptNo);
        model.addAttribute("leader", leader);

        return "approval/form-list";
    }

}
