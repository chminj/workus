package com.example.workus.approval.service;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.mapper.ApprovalMapper;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.attendance.mapper.AttendanceMapper;
import com.example.workus.attendance.service.AttendanceService;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ApprovalCategory> getCategories() {
        return approvalMapper.getAllCategories();
    }

    public User getLeader(Long deptNo) {
        return userMapper.getLeaderByDeptNo(deptNo);
    }

    public List<User> getUserByRole(int roleNo) {
        return approvalMapper.getUsersByRoleNo(roleNo);
    }

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

        // apvNo가 null인지 확인
        if (apvNo == null) {
            throw new RuntimeException("APV_NO가 null입니다. INSERT가 실패했습니다.");
        }

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

    private void validateApprovalForm(ApvApprovalForm form) {
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
    }

}
