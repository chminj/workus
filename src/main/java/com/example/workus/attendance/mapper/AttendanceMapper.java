package com.example.workus.attendance.mapper;

import com.example.workus.attendance.dto.AttendanceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

@Mapper
@Alias("Attendance")
public interface AttendanceMapper {

    AttendanceDto getAttendanceByUserNo(@Param("userNo") Long userNo);
}
