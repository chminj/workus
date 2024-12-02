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

    // attendance/list 잔여 연차, 총 연차 조회
    AttendanceDto getAttendanceByUserNo(@Param("userNo") Long userNo);

    // 신청 가능한 근태 종류 조회
    List<AttendanceCategory> getAllCategories();

    // 연차 신청 정보 추가
    void insertApproval(@Param("form") ApprovalForm form);

    // 연차 결재 요청자, 참조자 정보 추가
    void insertApprovalUsers(@Param("users") List<ApprovalUserDto> users, @Param("form") ApprovalForm form);

    // 내 연차 결재 요청 목록 조회
    List<ReqViewDto> getAllRequestFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    int getTotalRows(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    // 내가 참조자인 목록 조회
    List<RefViewDto> getAllReferenceFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    // 권한 조회
    int getUserRoleNo(@Param("userNo") Long userNo);

    // 내가 결재자인 목록 조회
    List<ApvViewDto> getAllApprovalFormsByUserNo(@Param("userNo") Long userNo, @Param("condition") Map<String, Object> condition);

    // 승인 로직 시작
    // 연차 이력 추가에 필요한 데이터 조회(H.잔여 연차, C.카테고리 별 차감 일수)
    Map<String, Object> getAnnualLeaveData(@Param("atdNo") Long atdNo);

    // 승인 이력에 추가
    void insertAnnualLeaveHistory(@Param("apvReqDto") ApprovalRequestDto approvalRequestDto);

    // 승인 상태 변경
    void updateStatusByAtdNo(@Param("atdNo") Long atdNo);

    // 승인 이후 잔여 연차 수정
    void updateAnnualLeaveByUnusedDate();
    // 승인 로직 종료

    // 일반 사용자 : 팀원 연차 이력 전체 조회
    List<AnnualLeaveHistoryDto> getUsedAnnualLeaveByUser(@Param("user") User user);

    // 관리자 : 이용자 연차 이력 전체 조회
    List<AnnualLeaveHistoryDto> getAllUsedAnnualLeave();

}
