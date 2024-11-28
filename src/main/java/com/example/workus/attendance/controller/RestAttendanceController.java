package com.example.workus.attendance.controller;

import com.example.workus.attendance.dto.ApprovalRequestDto;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.common.dto.RestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendances")
public class RestAttendanceController {

    @Autowired
    private AttendanceService attendanceService;

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
}
