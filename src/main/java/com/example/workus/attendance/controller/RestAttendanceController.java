package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.AnnualLeaveHistoryDto;
import com.example.workus.attendance.dto.AtdApprovalRequestDto;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.common.vo.Constants;
import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.security.LoginUser;
import com.example.workus.user.dto.DeptDto;
import com.example.workus.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class RestAttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;

    @PostMapping("/approve")
    public ResponseEntity<RestResponseDto<String>> approveRequests(@RequestBody List<AtdApprovalRequestDto> requestDtoList) {
        for (AtdApprovalRequestDto reqDto : requestDtoList) {
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
    public ResponseEntity<List<AnnualLeaveHistoryDto>> getAnnualLeaveHistory(@AuthenticationPrincipal LoginUser loginUser) {
        List<AnnualLeaveHistoryDto> events;
        int roleNo = attendanceService.getUserRoleNo(loginUser.getNo());
        // 권한 구분
        if (roleNo == Constants.ROLE_NO_ADMIN) {
            events = attendanceService.getAllAnnualLeaveHistory();
        } else {
            events = attendanceService.getAnnualLeaveHistoryForLoggedInUser(loginUser.getNo());
        }

        return ResponseEntity.ok(events);
    }

    /**
     * 필터링할 부서 정보를 조회한다.
     *
     * @param loginUser 로그인한 사용자 정보
     * @return 조회 가능한 부서 목록
     */
    @GetMapping("/departments")
    public ResponseEntity<List<DeptDto>> getDepartments(@AuthenticationPrincipal LoginUser loginUser) {
        List<DeptDto> depts;
        int roleNo = attendanceService.getUserRoleNo(loginUser.getNo());

        if (roleNo == Constants.ROLE_NO_ADMIN) {
            // 관리자일 경우 모든 부서 목록을 가져옴
            depts = userService.getAllDepts();
        } else {
            // 일반 사용자일 경우 본인 부서만 조회
            depts = userService.getDeptsForUser(loginUser.getNo());
        }

        return ResponseEntity.ok(depts);
    }

    /**
     * 연차 이력 조회 시 사용할 권한 정보를 조회한다. (권한에 따라서 연차 이력 조회가 제한된다.)
     *
     * @param loginUser 로그인한 사용자 정보
     * @return roleNo
     */
    @GetMapping("/role")
    public ResponseEntity<Integer> getUserRole(@AuthenticationPrincipal LoginUser loginUser) {
        int roleNo = attendanceService.getUserRoleNo(loginUser.getNo());
        return ResponseEntity.ok(roleNo);
    }

}
