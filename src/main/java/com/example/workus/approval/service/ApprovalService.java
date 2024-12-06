package com.example.workus.approval.service;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.dto.RefListViewDto;
import com.example.workus.approval.dto.ReqListViewDto;
import com.example.workus.approval.dto.WaitListViewDto;
import com.example.workus.approval.mapper.ApprovalMapper;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApprovalService {

    private final ApprovalMapper approvalMapper;
    private final UserMapper userMapper;

    @Autowired
    public ApprovalService(ApprovalMapper approvalMapper, UserMapper userMapper) {
        this.approvalMapper = approvalMapper;
        this.userMapper = userMapper;
    }

    /**
     * 신청할 수 있는 결재 문서의 종류를 조회한다.
     *
     * @return 결재 문서 종류들
     */
    public List<ApprovalCategory> getCategories() {
        return approvalMapper.getAllCategories();
    }

    /**
     * 팀장의 정보를 부서 번호로 조회한다.
     *
     * @param deptNo 부서 번호
     * @return 팀장 정보
     */
    public User getLeader(Long deptNo) {
        return userMapper.getLeaderByDeptNo(deptNo);
    }

    /**
     * 권한 번호로 해당 권한이 있는 유저들을 조회한다.
     *
     * @param roleNo 권한 번호
     * @return 권한을 갖고 있는 유저들
     */
    public List<User> getUserByRole(int roleNo) {
        return approvalMapper.getUsersByRoleNo(roleNo);
    }

    /**
     * 결재 양식을 작성하고 결재를 요청한다.
     *
     * @param form 결재 양식
     */
    public void addForm(ApvApprovalForm form) {
        validateApprovalForm(form);

        ApvApprovalForm apvBase = new ApvApprovalForm();
        apvBase.setUserNo(form.getUserNo());
        apvBase.setCategoryNo(form.getCategoryNo());
        apvBase.setTitle(form.getTitle());
        apvBase.setFromDate(form.getFromDate());
        apvBase.setReason(form.getReason());
        apvBase.setCommonText(form.getCommonText());

        approvalMapper.insertApprovalFormBase(apvBase);
        Long apvNo = apvBase.getNo();

        // 추가적인 데이터 삽입이 필요한 경우
        if (form.getOptionTexts() != null) {
            Map<String, String> texts = form.getOptionTexts();

            for (Map.Entry<String, String> entry : texts.entrySet()) {
                String termName = entry.getKey();
                String termValue = entry.getValue();

                approvalMapper.insertApprovalFormOption(termName, termValue, apvNo);
            }
        }
    }

    /**
     * 결재 양식에서 유효성을 검사한다.
     *
     * @param form 결재 양식
     */
    private void validateApprovalForm(ApvApprovalForm form) {
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
    }

    public List<ReqListViewDto> getMyReqList(Long userNo) {
        return approvalMapper.getReqList(userNo);
    }

    /**
     * 결재 대기 건을 조회한다.
     *
     * @return 결재 대기 건 리스트
     */
    public List<ReqListViewDto> getMyWaitList() {
        return approvalMapper.getWaitList();
    }

    /**
     * 팀장은 팀원들의 결재 요청들을 내 공람 내역에서 조회한다.
     *
     * @param leaderNo 팀장 번호
     * @return 팀원들의 결재 요청 리스트
     */
    public List<ReqListViewDto> getMyRefList(Long leaderNo) {
        return approvalMapper.getRefListByLeaderNo(leaderNo);
    }

}
