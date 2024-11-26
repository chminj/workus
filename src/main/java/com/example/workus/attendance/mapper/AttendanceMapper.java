package com.example.workus.attendance.mapper;

import com.example.workus.attendance.dto.ApprovalForm;
import com.example.workus.attendance.dto.ApprovalUserDto;
import com.example.workus.attendance.dto.AttendanceCategoryDto;
import com.example.workus.attendance.dto.AttendanceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Mapper
@Alias("Attendance")
public interface AttendanceMapper {

    AttendanceDto getAttendanceByUserNo(@Param("userNo") Long userNo);

    List<AttendanceCategoryDto> getAllCategories();

    void insertApproval(@Param("form") ApprovalForm form);

    void insertApprovalUsers(@Param("users") List<ApprovalUserDto> users, @Param("form") ApprovalForm form);
}
