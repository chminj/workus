package com.example.workus.attendance.service;

import com.example.workus.attendance.dto.*;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    /**
     * 폼에 맞는 값을 작성하여 연차 결재를 요청한다.
     * @param form 연차 신청 폼
     */
    public void insertApprovalForm(ApprovalForm form, List<ApprovalUserDto> users) {
        attendanceMapper.insertApproval(form);
        attendanceMapper.insertApprovalUsers(users, form);
    }

    /**
     * 내가 신청한 연차 신청 내역을 조회한다.
     * @param userNo 로그인한 유저
     * @param condition 페이징, 검색조건(checkbox 1)
     * @return 내 신청 내역 리스트
     */
    public ListDto<ReqViewDto> getRequestForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(totalRows, page, rows);

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        condition.put("begin", begin);
        condition.put("end", end);

        condition.compute("status", (k, status) -> status);

        // 조회범위에 맞는 데이터 조회
        List<ReqViewDto> forms = attendanceMapper.getAllRequestFormsByUserNo(userNo, condition);

        ListDto<ReqViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    /**
     * 내가 참조자로 추가된 근태 신청 내역을 조회한다.
     * @param userNo 로그인한 참조자 번호
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
        condition.put("begin", begin);
        condition.put("end", end);

        List<RefViewDto> forms = attendanceMapper.getAllReferenceFormsByUserNo(userNo, condition);

        ListDto<RefViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    public ListDto<ApvViewDto> getApprovalForms(Long userNo, Map<String, Object> condition) {
        int totalRows = attendanceMapper.getTotalRows(userNo, condition);
        int page = (Integer) condition.get("page");
        int rows = (Integer) condition.get("rows");
        Pagination pagination = new Pagination(totalRows, page, rows);

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        condition.put("begin", begin);
        condition.put("end", end);

        List<ApvViewDto> forms = attendanceMapper.getAllApprovalFormsByUserNo(userNo, condition);

        ListDto<ApvViewDto> dtoList = new ListDto<>(forms, pagination);

        return dtoList;
    }

    public boolean approveRequests(ApprovalRequestDto approvalRequestDto) {

        // 상태 업데이트
//        int updatedCount = attendanceMapper.updateRequestStatus(approvalRequestDto.getAtdNo());

        // 잔여 연차 일수 차감 로직 추가 (하루 이상일 경우)
//        if (updatedCount > 1) {
//            attendanceMapper.deductAnnualLeave(approvalRequestDto.getDayTotal());
//        }

//        return updatedCount > 0;

        return attendanceMapper.updateRequestStatus(approvalRequestDto.getAtdNo());
    }
}
