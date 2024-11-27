package com.example.workus.attendance.mapper;

import com.example.workus.attendance.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.Map;

@Mapper
@Alias("Attendance")
public interface AttendanceMapper {

    AttendanceDto getAttendanceByUserNo(@Param("userNo") Long userNo);
    List<AttendanceCategoryDto> getAllCategories();

    void insertApproval(@Param("form") ApprovalForm form);
    void insertApprovalUsers(@Param("users") List<ApprovalUserDto> users, @Param("form") ApprovalForm form);

    List<ReqViewDto> getAllRequestFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);
    int getTotalRows(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);
    List<RefViewDto> getAllReferenceFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);
    List<ApvViewDto> getAllApprovalFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    boolean updateRequestStatus(List<Long> atdNo);
}
