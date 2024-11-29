package com.example.workus.attendance.mapper;

import com.example.workus.attendance.dto.*;
import com.example.workus.attendance.vo.AttendanceCategory;
import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
@Alias("Attendance")
public interface AttendanceMapper {

    AttendanceDto getAttendanceByUserNo(@Param("userNo") Long userNo);

    List<AttendanceCategory> getAllCategories();

    void insertApproval(@Param("form") ApprovalForm form);

    void insertApprovalUsers(@Param("users") List<ApprovalUserDto> users, @Param("form") ApprovalForm form);

    List<ReqViewDto> getAllRequestFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    int getTotalRows(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    List<RefViewDto> getAllReferenceFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    int getUserRoleNo(@Param("userNo") Long userNo);

    List<ApvViewDto> getAllApprovalFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    void insertAnnualLeaveHistory(@Param("apvReqDto") ApprovalRequestDto approvalRequestDto);

    void updateStatusByAtdNo(@Param("atdNo") Long atdNo);

    void updateAnnualLeaveByDayTotal(@Param("dayTotal") BigDecimal dayTotal);

    void updateAnnualLeaveByCtgrCount();

    Map<String, Object> getAnnualLeaveData(@Param("atdNo") Long atdNo);

    List<AnnualLeaveHistoryDto> getUsedAnnualLeaveByUser(@Param("user") User user);

    List<AnnualLeaveHistoryDto> getAllUsedAnnualLeave();
}
