package com.example.workus.attendance.service;

import com.example.workus.attendance.dto.AttendanceCategoryDto;
import com.example.workus.attendance.dto.AttendanceDto;
import com.example.workus.attendance.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 연차 신청 폼에서 선택할 수 있는 연차 이름을 보여준다.
     * @return 연차 종류 이름
     */
    public List<AttendanceCategoryDto> getCategories() {
        return attendanceMapper.getAllCategories();
    }
}
