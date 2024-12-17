package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.*;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.attendance.vo.AttendanceCategory;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.exception.RestAttendanceException;
import com.example.workus.security.LoginUser;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ListBucketAnalyticsConfigurationsRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, UserService userService) {
        this.attendanceService = attendanceService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal LoginUser loginUser
                        , Model model)
    {
        AttendanceDto attendanceDto = attendanceService.getAttendance(loginUser.getNo());
        model.addAttribute("attendanceDto", attendanceDto);

        int myApvCount = attendanceService.getTotalRowsMyApv(loginUser.getNo());
        model.addAttribute("myApvCount", myApvCount);

        return "attendance/list";
    }

    @GetMapping("/atdFormInUser")
    @ResponseBody
    public ResponseEntity<List<User>> usersInAtdForm(@AuthenticationPrincipal LoginUser loginUser) {
        try {
            List<User> users = (List<User>) userService.getUsersExceptMe(loginUser.getNo());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RestAttendanceException("사용자 목록 조회 중 오류 발생", e);
        }
    }

    @GetMapping("/atdFormInCtgr")
    @ResponseBody
    public ResponseEntity<List<AttendanceCategory>> categoriesInAtdForm() {
    try {
        List<AttendanceCategory> categories = (List<AttendanceCategory>) attendanceService.getCategories();
        return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RestAttendanceException("카테고리 목록 조회 중 오류 발생", e);
        }
    }

    @PostMapping("/getApproval")
    public String getApproval(AtdApprovalForm form
                            , @AuthenticationPrincipal LoginUser loginUser
                            , @RequestParam(required = false) int dayTotal)
    {
        AtdApprovalForm apvForm = new AtdApprovalForm();
        apvForm.setNo(form.getNo());
        apvForm.setReason(form.getReason());
        apvForm.setFromDate(form.getFromDate());
        apvForm.setToDate(form.getToDate());
        apvForm.setCategoryNo(form.getCategoryNo());
        apvForm.setUserNo(loginUser.getNo());
        apvForm.setTime(form.getTime());
        apvForm.setDayTotal(form.getDayTotal());
        List<AtdApprovalUserDto> users = new ArrayList<>();
        String[] apvs = form.getApv().split(",");
        String[] refs = form.getRef().split(",");
        // 승인자 리스트 추가
        int index = 0;
        for (String value : apvs) {
            Long intValue = Long.valueOf(value);

            AtdApprovalUserDto userDto = AtdApprovalUserDto.builder()
                    .status("A")
                    .userNo(intValue)
                    .sequence(index++)
                    .formNo(form.getNo())
                    .build();
            users.add(userDto);
        }
        // 참조자 리스트 추가
        index = 0;
        for (String value : refs) {
            Long intValue = Long.valueOf(value);
            AtdApprovalUserDto userDto = AtdApprovalUserDto.builder()
                    .status("R")
                    .userNo(intValue)
                    .sequence(index++)
                    .formNo(form.getNo())
                    .build();
            users.add(userDto);
        }

        // ApprovalRequestDto 생성 및 dayTotal 설정
        AtdApprovalRequestDto atdApprovalRequestDto = new AtdApprovalRequestDto();
        atdApprovalRequestDto.setAtdNo(form.getNo());
        atdApprovalRequestDto.setDayTotal(BigDecimal.valueOf(dayTotal));

        attendanceService.insertApprovalForm(apvForm, users);

        return "redirect:/attendance/list";
    }

    @GetMapping("/myReqList")
    public String myApvList(@AuthenticationPrincipal LoginUser loginUser
                            , Model model
                            , @RequestParam(required = false, defaultValue = "1") int page
                            , @RequestParam(required = false, defaultValue = "10") int rows
                            , @RequestParam(required = false) String status)
    {
        Map<String, Object> condition = new HashMap<>();
        condition.put("page", page);
        condition.put("rows", rows);
        if (StringUtils.hasText(status)) {
            condition.put("status", status);
        }

        ListDto<ReqViewDto> forms = attendanceService.getRequestForms(loginUser.getNo(), condition);

        model.addAttribute("condition", condition);
        model.addAttribute("forms", forms.getData());
        model.addAttribute("paging", forms.getPaging());

        return "attendance/myReqList";
    }

    @GetMapping("/myApvList")
    public String myReqList(@AuthenticationPrincipal LoginUser loginUser
                            , Model model
                            , @RequestParam(required = false, defaultValue = "1") int page
                            , @RequestParam(required = false, defaultValue = "10") int rows
                            , @RequestParam(required = false) String opt
                            , @RequestParam(required = false) String keyword)
    {
        Map<String, Object> condition = new HashMap<>();
        condition.put("page", page);
        condition.put("rows", rows);
        if (StringUtils.hasText(opt)) {
            condition.put("opt", opt);
            condition.put("keyword", keyword);
        }

        ListDto<ApvViewDto> forms = attendanceService.getApprovalForms(loginUser.getNo(), condition);
        model.addAttribute("condition", condition);
        model.addAttribute("forms", forms.getData());
        model.addAttribute("paging", forms.getPaging());

        return "attendance/myApvList";
    }

    @GetMapping("/myRefList")
    public String myRefList(@AuthenticationPrincipal LoginUser loginUser
                            , Model model
                            , @RequestParam(required = false, defaultValue = "1") int page
                            , @RequestParam(required = false, defaultValue = "10") int rows
                            , @RequestParam(required = false) String opt
                            , @RequestParam(required = false) String keyword)
    {
        Map<String, Object> condition = new HashMap<>();
        condition.put("page", page);
        condition.put("rows", rows);
        if (StringUtils.hasText(opt)) {
            condition.put("opt", opt);
            condition.put("keyword", keyword);
        }

        int roleNo = attendanceService.getUserRoleNo(loginUser.getNo());
        condition.put("roleNo", roleNo);

        ListDto<RefViewDto> forms;
        if (roleNo == 100 ){
            forms = attendanceService.getReferenceForms(loginUser.getNo(), condition, roleNo);
        } else {
            forms = attendanceService.getMyReferenceForms(loginUser.getNo(), condition);
        }

        model.addAttribute("condition", condition);
        model.addAttribute("forms", forms.getData());
        model.addAttribute("paging", forms.getPaging());

        return "attendance/myRefList";
    }
}
