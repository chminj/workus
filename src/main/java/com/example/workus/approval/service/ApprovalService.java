package com.example.workus.approval.service;

import com.example.workus.approval.mapper.ApprovalMapper;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public User getLeader(long deptNo) {
        return userMapper.getLeaderByDeptNo(deptNo);
    }
}
