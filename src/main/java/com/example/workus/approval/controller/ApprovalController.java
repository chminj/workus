package com.example.workus.approval.controller;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.dto.ApvApprovalRequestDto;
import com.example.workus.approval.dto.ApvDetailViewDto;
import com.example.workus.approval.dto.ApvListViewDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String list(@AuthenticationPrincipal LoginUser loginUser
                        , Model model)
    {

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
                            , @AuthenticationPrincipal LoginUser loginUser)
    {
        // 로그인한 userNo 설정
        Long userNo = loginUser.getNo();
        apvFormBase.setUserNo(userNo);

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

    @GetMapping("/my/reqList")
    public String myRequestList(@AuthenticationPrincipal LoginUser loginUser
                                , Model model)
    {
        List<ApvListViewDto> reqList = approvalService.getMyReqList(loginUser.getNo());
        model.addAttribute("reqList", reqList);

        return "approval/my/reqList";
    }

    @GetMapping("/my/waitList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String myApprovalList(Model model)
    {
        List<ApvListViewDto> waitList = approvalService.getMyWaitList();
        model.addAttribute("waitList", waitList);

        return "approval/my/waitList";
    }

    @GetMapping("/my/endList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String myEndList(Model model)
    {
        List<ApvListViewDto> endList = approvalService.getMyEndList();
        model.addAttribute("endList", endList);

        return "approval/my/endList";
    }

    @GetMapping("/my/refList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LEADER')")
    public String myReferenceList(@AuthenticationPrincipal LoginUser loginUser
                                    , Model model)
    {
        Long leaderNo = loginUser.getNo();
        List<ApvListViewDto> refList = approvalService.getMyRefList(leaderNo);

        model.addAttribute("refList", refList);

        return "approval/my/refList";
    }

//        @GetMapping("/my/detail/{apvNo}")
    @GetMapping("/my/detail/reqDetail")
    public String myRequestDetail(@RequestParam("no") Long no
                                , Model model)
    {
        ApvDetailViewDto reqByNo = approvalService.getMyReqDetail(no);
        model.addAttribute("reqByNo", reqByNo);

        return "approval/my/detail/reqDetail";
    }

    @GetMapping("/my/detail/waitDetail")
    public String myWaitDetail(@RequestParam("no") Long no
                                , Model model)
    {
        ApvDetailViewDto waitByNo = approvalService.getMyReqDetail(no);
        model.addAttribute("waitByNo", waitByNo);

        return "approval/my/detail/waitDetail";
    }

    @GetMapping("/my/detail/refDetail")
    public String myRefDetail(@RequestParam("no") Long no
                                , Model model)
    {
        ApvDetailViewDto refByNo = approvalService.getMyReqDetail(no);
        model.addAttribute("refByNo", refByNo);

        return "approval/my/detail/refDetail";
    }

    @PostMapping("/approve")
    public String approveRequest(@ModelAttribute ApvApprovalRequestDto requestDto
                                , @AuthenticationPrincipal LoginUser loginUser
                                , RedirectAttributes redirectAttributes)
    {
        // 승인 버튼 클릭한 로그인 유저 정보 담기
        requestDto.setReqUserNo(loginUser.getNo());
        // 승인 로직 처리
        approvalService.approveRequest(requestDto);

        // 리다이렉트 시 전달할 메시지 설정
        redirectAttributes.addFlashAttribute("message", "승인이 완료되었습니다.");

        return "redirect:/approval/my/endList";
    }
}
