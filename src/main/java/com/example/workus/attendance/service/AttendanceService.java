package com.example.workus.attendance.service;

import com.example.workus.attendance.dto.AttendanceDto;
import com.example.workus.attendance.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 로그인한 사용자가 근태 페이지에서 본인 근태 정보를 조회할 수 있다.
     * @param userNo 로그인한 사용자 번호
     * @return 사용자 연차 정보
     */
    public AttendanceDto getAttendance(Long userNo) {
        return attendanceMapper.getAttendanceByUserNo(userNo);
    }
}
