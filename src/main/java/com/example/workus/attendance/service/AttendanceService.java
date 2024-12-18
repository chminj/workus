package com.example.workus.attendance.service;

import com.example.workus.attendance.dto.*;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.attendance.vo.AttendanceCategory;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.exception.AttendanceException;
import com.example.workus.common.util.Pagination;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
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
        try {
            return attendanceMapper.getAttendanceByUserNo(userNo);
        } catch (Exception e) {
            log.error("Error fetching attendance for userNo: {}", userNo, e);
            throw new AttendanceException("ATTENDANCE_FETCH_ERROR", "근태 정보를 조회하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 결재 대기 건의 개수를 조회한다.
     *
     * @param userNo 로그인한 사용자 번호
     * @return 결재 필요한 건의 개수
     */
    public int getTotalRowsMyApv(Long userNo) {
        try {
            return attendanceMapper.getAtdApprovalCount(userNo);
        } catch (Exception e) {
            log.error("Error fetching approval count for userNo: {}", userNo, e);
            throw new AttendanceException("APPROVAL_COUNT_ERROR", "결재 대기 건수를 조회하는 중 오류가 발생했습니다.");
        }
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
        try {
            attendanceMapper.insertApproval(form);
            attendanceMapper.insertApprovalUsers(users, form);
        } catch (Exception e) {
            log.error("Error inserting approval form: {}", form, e);
            throw new AttendanceException("INSERT_APPROVAL_FORM_ERROR", "연차 결재 요청을 추가하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 내가 신청한 연차 신청 내역을 조회한다.
     *
     * @param userNo    로그인한 유저
     * @param condition 페이징, 검색조건(checkbox 1)
     * @return 내 신청 내역 리스트
     */
    public ListDto<ReqViewDto> getRequestForms(Long userNo, Map<String, Object> condition) {
        try {
            int totalRows = attendanceMapper.getMyTotalRows(userNo, condition);
            int page = (Integer) condition.get("page");
            int rows = (Integer) condition.get("rows");
            Pagination pagination = new Pagination(page, totalRows, rows);
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

            // 조회범위에 맞는 데이터 조회
            List<ReqViewDto> forms = attendanceMapper.getAllRequestFormsByUserNo(userNo, condition);

            ListDto<ReqViewDto> dtoList = new ListDto<>(forms, pagination);

            return dtoList;
        } catch (Exception e) {
            log.error("Error fetching request forms for userNo: {}", userNo, e);
            throw new AttendanceException("REQUEST_FORMS_FETCH_ERROR", "신청 내역을 조회하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 로그인한 유저의 권한을 조회한다.
     *
     * @param userNo 로그인한 유저 번호
     * @return 권한 번호
     */
    public int getUserRoleNo(Long userNo) {
        try {
            return userMapper.getUserRoleNo(userNo);
        } catch (Exception e) {
            log.error("Error fetching user role for userNo: {}", userNo, e);
            throw new AttendanceException("USER_ROLE_ERROR", "사용자 권한 정보를 조회하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 내가 참조자로 추가된 근태 신청 내역을 조회한다.
     *
     * @param userNo    로그인한 참조자 번호
     * @param condition 페이징, 검색 조건(date, opt, keyword)
     * @return 내 참조 내역 리스트
     */
    public ListDto<RefViewDto> getMyReferenceForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getMyRefTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(page, totalRows, rows);

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        int offset = pagination.getOffset();
        if (begin < 0) {
            begin = 1; // 기본값 설정
        }
        if (end <= 0) {
            end = 10; // 기본값 설정
        }
        condition.put("begin", begin);
        condition.put("offset", offset);
        condition.put("end", end);

        List<RefViewDto> forms = attendanceMapper.getAllReferenceFormsByUserNo(userNo, condition);
        ListDto<RefViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    public ListDto<RefViewDto> getReferenceForms(Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(page, totalRows, rows);

        int offset = pagination.getOffset();

        condition.put("offset", offset);
        condition.put("rows", rows);

        List<RefViewDto> forms = attendanceMapper.getAllReferenceFormsByRoleNo(condition);

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
        int totalRows = attendanceMapper.getMyApvTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");

        Pagination pagination = new Pagination(page, totalRows, rows);

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

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 승인 로직 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /**
     * 연차 이력 테이블 내 차감될 연차 컬럼에 사용할 데이터(직급별 총 연차에서 잔여 연차, 연차 종류별 차감될 연차)를 가져온다.
     *
     * @param atdNo 연차 신청 글 PK
     * @return
     */
    private Map<String, Object> getAnnualLeaveData(Long atdNo) {
        Map<String, Object> data = attendanceMapper.getAnnualLeaveData(atdNo);
        if (data == null || data.isEmpty()) {
            throw new AttendanceException("ANNUAL_LEAVE_DATA_ERROR", "연차 데이터가 없습니다. atdNo: " + atdNo);
        }
        return data;
    }

    /**
     * 요청 폼을 INSERT할 때 사용할 값을 조건에 따라 다르게 세팅한다.
     *
     * @param dto 연차 신청 폼
     * @param unusedLeave 잔여 연차
     * @param categoryCount 연차 종류별 차감될 숫자
     * @param updatedCount 2일 이상의 총 일수
     */
    private void setAnnualLeaveData(AtdApprovalRequestDto dto, BigDecimal unusedLeave, BigDecimal categoryCount, Integer updatedCount) {
        if (updatedCount != null && updatedCount != 0) { // 연차 종류에서 차감될 count를 조회하지 않고 updatedCount를 사용
            dto.setUsedDate(BigDecimal.valueOf(updatedCount));
            dto.setUnusedDate(unusedLeave.subtract(BigDecimal.valueOf(updatedCount)));
            dto.setTotalDay(updatedCount);
        } else { // 연차 종류에서 count를 조회해서 사용
            dto.setUsedDate(categoryCount);
            dto.setUnusedDate(unusedLeave.subtract(categoryCount));
            dto.setTotalDay(categoryCount.intValue());
        }
    }

    /**
     * 결재자가 모두 승인했는지 status를 조회한다.
     *
     * @param approvalStatusList 컬럼명(key)과 status 컬럼의 도메인 값(value) 형식의 리스트
     * @return 모든 결재자가 승인하면 true, 그렇지 않으면 false
     */
    private boolean areAllApproved(List<Map<String, Object>> approvalStatusList) {
        for (Map<String, Object> statusData : approvalStatusList) {
            String status = (String) statusData.get("status");
            if (!"C".equals(status)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 결재 요청이 온 연차 신청을 승인하여 승인 완료 처리한다.
     *
     * @param requestDtoList 요청 승인 리스트
     */
    public void approveRequests(List<AtdApprovalRequestDto> requestDtoList) {
        try {
            // 일괄 승인 처리 대비해서 List로 받아와서 반복문 돌림
            for (AtdApprovalRequestDto atdApprovalRequestDto : requestDtoList) {
                // 필요한 데이터 가져오기
                Map<String, Object> annualLeaveData = getAnnualLeaveData(atdApprovalRequestDto.getAtdNo());
                // 잔여연차, 연차 종류별 차감일, (하계휴가일 경우 즉, 2일 이상일 때 사용할) 총 일수 조회
                BigDecimal unusedLeave = (BigDecimal) annualLeaveData.get("unused_leave");
                BigDecimal categoryCount = (BigDecimal) annualLeaveData.get("category_count");
                Integer updatedCount = (int) annualLeaveData.get("total_day");
                if (updatedCount == null) {
                    // null check + default 값 설정
                    updatedCount = 0;
                }

                // 1) 연차 이력 추가
                setAnnualLeaveData(atdApprovalRequestDto, unusedLeave, categoryCount, updatedCount);

                // 2) 상태 업데이트
                attendanceMapper.updateStatusByAtdNoAndUserNo(atdApprovalRequestDto.getAtdNo(), atdApprovalRequestDto.getApprovalNo());

                // 3-1) 각 결재자가 승인했는지 조회
                List<Map<String, Object>> approvalStatusList = attendanceMapper.getApprovalStatus(atdApprovalRequestDto.getAtdNo());

                // 3-2) 모두 승인한 건일 경우 추가 로직 진행
                if (areAllApproved(approvalStatusList)) {
                    // 4) 승인된 연차 이력에 추가
                    attendanceMapper.insertAnnualLeaveHistory(atdApprovalRequestDto);
                    // 5) 참조건 상태 변경
                    attendanceMapper.updateStatusByAtdNo(atdApprovalRequestDto.getAtdNo());
                    // 6) 잔여 연차 차감
                    attendanceMapper.updateAnnualLeaveByUnusedDate();
                }
            }
        } catch (Exception e) {
            log.error("Error approving requests: {}", requestDtoList, e);
            throw new AttendanceException("APPROVE_REQUESTS_ERROR", "결재 요청을 승인하는 중 오류가 발생했습니다.");
        }
    }
    /* // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 승인 로직 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /**
     * 근태 이력 팀원 조회
     *
     * @param userNo 로그인한 유저 번호
     * @return 팀원의 승인된 근태 이력 목록
     */
    public List<AnnualLeaveHistoryDto> getAnnualLeaveHistoryForLoggedInUser(Long userNo) {
        try {
            User user = userMapper.getUserByUserNo(userNo);
            return attendanceMapper.getUsedAnnualLeaveByUser(user);
        } catch (Exception e) {
            log.error("Error fetching annual leave history for userNo: {}", userNo, e);
            throw new AttendanceException("USER_ANNUAL_LEAVE_HISTORY_ERROR", "로그인한 사용자의 연차 이력을 조회하는 중 오류가 발생했습니다.");
        }

    }

    /**
     * 근태 이력 관리자 조회
     *
     * @return 승인된 근태 이력 목록 전체
     */
    public List<AnnualLeaveHistoryDto> getAllAnnualLeaveHistory() {
        try {
            return attendanceMapper.getAllUsedAnnualLeave();
        } catch (Exception e) {
            log.error("Error fetching all annual leave history", e);
            throw new AttendanceException("ANNUAL_LEAVE_HISTORY_ERROR", "모든 연차 이력을 조회하는 중 오류가 발생했습니다.");
        }
    }

}
