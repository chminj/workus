package com.example.workus.attendance.service;

import com.example.workus.attendance.dto.*;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.attendance.vo.AttendanceCategory;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.util.Pagination;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;
    private final UserMapper userMapper;

    @Autowired
    public AttendanceService(AttendanceMapper attendanceMapper, UserMapper userMapper) {
        this.attendanceMapper = attendanceMapper;
        this.userMapper = userMapper;
    }

    /**
     * 로그인한 사용자가 근태 페이지에서 본인 근태 정보를 조회할 수 있다.
     *
     * @param userNo 로그인한 사용자 번호
     * @return 사용자 연차 정보
     */
    public AttendanceDto getAttendance(Long userNo) {
        return attendanceMapper.getAttendanceByUserNo(userNo);
    }

    /**
     * 결재 대기 건의 개수를 조회한다.
     *
     * @param userNo 로그인한 사용자 번호
     * @return 결재 필요한 건의 개수
     */
    public int getTotalRowsMyApv(Long userNo) {
        return attendanceMapper.getAtdApprovalCount(userNo);
    }

    /**
     * 연차 신청 폼에서 선택할 수 있는 연차 이름을 보여준다.
     *
     * @return 연차 종류 이름
     */
    public List<AttendanceCategory> getCategories() {
        return attendanceMapper.getAllCategories();
    }

    /**
     * 폼에 맞는 값을 작성하여 연차 결재를 요청한다.
     *
     * @param form 연차 신청 폼
     */
    public void insertApprovalForm(AtdApprovalForm form, List<AtdApprovalUserDto> users) {
        attendanceMapper.insertApproval(form);
        attendanceMapper.insertApprovalUsers(users, form);
    }

    /**
     * 내가 신청한 연차 신청 내역을 조회한다.
     *
     * @param userNo    로그인한 유저
     * @param condition 페이징, 검색조건(checkbox 1)
     * @return 내 신청 내역 리스트
     */
    public ListDto<ReqViewDto> getRequestForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(totalRows, page, rows);

        int begin = pagination.getBegin();
        int offset = pagination.getOffset();
        int end = pagination.getEnd();
        if (begin < 0) {
            begin = 0; // 기본값 설정
        }
        if (end <= 0) {
            end = 10; // 기본값 설정
        }
        condition.put("begin", begin);
        condition.put("offset", offset);
        condition.put("end", end);
        condition.compute("status", (k, status) -> status);

        System.out.println("condition: " + condition);

        // 조회범위에 맞는 데이터 조회
        List<ReqViewDto> forms = attendanceMapper.getAllRequestFormsByUserNo(userNo, condition);

        ListDto<ReqViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    /**
     * 로그인한 유저의 권한을 조회한다.
     *
     * @param userNo 로그인한 유저 번호
     * @return 권한 번호
     */
    public int getUserRoleNo(Long userNo) {
        return userMapper.getUserRoleNo(userNo);
    }

    /**
     * 내가 참조자로 추가된 근태 신청 내역을 조회한다.
     *
     * @param userNo    로그인한 참조자 번호
     * @param condition 페이징, 검색 조건(date, opt, keyword)
     * @return 내 참조 내역 리스트
     */
    public ListDto<RefViewDto> getReferenceForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(totalRows, page, rows);

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        int offset = pagination.getOffset();
        if (begin < 0) {
            begin = 0; // 기본값 설정
        }
        if (end <= 0) {
            end = 10; // 기본값 설정
        }
        condition.put("begin", begin);
        condition.put("offset", offset);
        condition.put("end", end);

        condition.compute("roleNo", (k, roleNo) -> roleNo);

        System.out.println("condition: " + condition);

        List<RefViewDto> forms = attendanceMapper.getAllReferenceFormsByUserNo(userNo, condition);

        ListDto<RefViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    /**
     * 내가 결재자로 추가된 근태 신청 내역을 조회한다.
     *
     * @param userNo    로그인한 결재자 번호
     * @param condition 페이징, 검색 조건(date, opt, keyword)
     * @return 내 결재 내역 리스트
     */
    public ListDto<ApvViewDto> getApprovalForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(totalRows, page, rows);

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        int offset = pagination.getOffset();
        if (begin < 0) {
            begin = 0; // 기본값 설정
        }
        if (end <= 0) {
            end = 10; // 기본값 설정
        }
        condition.put("begin", begin);
        condition.put("offset", offset);
        condition.put("end", end);

        List<ApvViewDto> forms = attendanceMapper.getAllApprovalFormsByUserNo(userNo, condition);

        ListDto<ApvViewDto> dtoList = new ListDto<>(forms, pagination);
        return dtoList;
    }

    /**
     * 결재 요청이 온 연차 신청을 승인하여 승인 완료 처리한다.
     *
     * @param atdApprovalRequestDto 요청 승인
     */
    public void approveRequests(AtdApprovalRequestDto atdApprovalRequestDto) {
        // 1) 연차 이력 추가를 위한 데이터 가져오기
        Map<String, Object> annualLeaveData = attendanceMapper.getAnnualLeaveData(atdApprovalRequestDto.getAtdNo());

        // 필요한 값 추출
        BigDecimal unusedLeave = (BigDecimal) annualLeaveData.get("unused_leave");
        BigDecimal categoryCount = (BigDecimal) annualLeaveData.get("category_count");
        Long updatedCount = (Long) annualLeaveData.get("total_day");

        // 2) 연차 이력 추가
        if (updatedCount != 0) {
            atdApprovalRequestDto.setUsedDate(BigDecimal.valueOf(updatedCount));
            atdApprovalRequestDto.setUnusedDate(unusedLeave.subtract(BigDecimal.valueOf(updatedCount)));
            atdApprovalRequestDto.setTotalDay(updatedCount.intValue());

        } else {
            atdApprovalRequestDto.setUsedDate(categoryCount);
            atdApprovalRequestDto.setUnusedDate(unusedLeave.subtract(categoryCount));
            atdApprovalRequestDto.setTotalDay(categoryCount.intValue());
        }
        attendanceMapper.insertAnnualLeaveHistory(atdApprovalRequestDto);

        // 3) 상태 업데이트
        attendanceMapper.updateStatusByAtdNo(atdApprovalRequestDto.getAtdNo());

        // 4) 잔여 연차 차감
        attendanceMapper.updateAnnualLeaveByUnusedDate();
    }

    /**
     * 근태 이력 팀원 조회
     *
     * @param userNo 로그인한 유저 번호
     * @return 팀원의 승인된 근태 이력 목록
     */
    public List<AnnualLeaveHistoryDto> getAnnualLeaveHistoryForLoggedInUser(Long userNo) {
        User user = userMapper.getUserByUserNo(userNo);
        return attendanceMapper.getUsedAnnualLeaveByUser(user);
    }

    /**
     * 근태 이력 관리자 조회
     *
     * @return 승인된 근태 이력 목록 전체
     */
    public List<AnnualLeaveHistoryDto> getAllAnnualLeaveHistory() {
        return attendanceMapper.getAllUsedAnnualLeave();
    }

}
