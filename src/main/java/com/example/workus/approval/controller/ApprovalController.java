package com.example.workus.approval.controller;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.service.ApprovalService;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.security.LoginUser;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        User user = userService.getUserByUserNo(userNo);
        Long deptNo = user.getDeptNo();
        // leader 열람/공람자에서 조회
        User leader = approvalService.getLeader(deptNo);
        model.addAttribute("leader", leader);

        return "approval/form-list";
    }

    @PostMapping("/addForm")
    public String addForm(@ModelAttribute ApvApprovalForm apvFormBase
            , @AuthenticationPrincipal LoginUser loginUser) {
        // 로그인한 userNo 설정
        Long userNo = loginUser.getNo();
        apvFormBase.setUserNo(userNo);
        // 공람자 설정 : 신청자의 팀장 조회
//        User user = userService.getUserByUserNo(userNo);
//        Long deptNo = user.getDeptNo();
//        User viewer = approvalService.getLeader(deptNo);
        // 결재자 설정 : 인사팀원 동적 조회
//        List<User> approvers = approvalService.getUserByRole(Constants.ROLE_NO_MANAGE);

        // categoryNo가 apvApprovalForm에 있어야 함 (가져온 categoryNo 설정)
        int categoryNo = apvFormBase.getCategoryNo();
        apvFormBase.setCategoryNo(categoryNo);

        // 기본값 담는 form Dto
        String title = apvFormBase.getTitle();
        Date fromDate = apvFormBase.getFromDate();
        String reason = apvFormBase.getReason();
        String commonText = apvFormBase.getCommonText();

        // approvalTextArea에 추가 텍스트(들) 처리
        Map<String, String> optionTexts = apvFormBase.getOptionTexts();

        approvalService.addForm(apvFormBase);

        return "redirect:/approval/form-list";
    }

    @GetMapping("/my/waitList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String myApprovalList(@AuthenticationPrincipal LoginUser loginUser, Model model) {

        return "approval/my/waitList";
    }

}
