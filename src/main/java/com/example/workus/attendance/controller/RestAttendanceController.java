package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.AnnualLeaveHistoryDto;
import com.example.workus.attendance.dto.ApprovalRequestDto;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.security.LoginUser;
import com.example.workus.user.dto.DeptDto;
import com.example.workus.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendances")
public class RestAttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;

    @PostMapping("/approve")
    public ResponseEntity<RestResponseDto<String>> approveRequests(@RequestBody List<ApprovalRequestDto> requestDtoList) {
        for (ApprovalRequestDto reqDto : requestDtoList) {
            // 각 승인 요청에 대해 서비스 메소드 호출
            attendanceService.approveRequests(reqDto);
        }
        // RestResponseDto의 success 메서드를 호출하여 기본 메시지로 응답 생성
        RestResponseDto<String> response = RestResponseDto.success(null); // 데이터는 null 또는 다른 데이터로 설정

        response.setMessage("승인이 완료되었습니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * 근태 메인 화면 fullCalendar에서 연차 이력을 조회한다.
     *
     * @param loginUser 로그인한 유저 정보
     * @return 연차 이력 목록
     */
    @PostMapping("/annualLeaveHistory")
    public ResponseEntity<Map<String, Object>> getAnnualLeaveHistory(@AuthenticationPrincipal LoginUser loginUser) {
        List<AnnualLeaveHistoryDto> events = null;
        List<DeptDto> depts = null;
        int roleNo = attendanceService.getUserRoleNo(loginUser.getNo());

        // 권한 구분
        if (roleNo == 100) {
            events = attendanceService.getAllAnnualLeaveHistory();
            depts = userService.getAllDepts();
        } else {
            events = attendanceService.getAnnualLeaveHistoryForLoggedInUser(loginUser.getNo());
            depts = userService.getDeptsForUser(loginUser.getNo());
        }

        return ResponseEntity.ok(Map.of("events", events, "depts", depts, "roleNo", roleNo));
    }


}
