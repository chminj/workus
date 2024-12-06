package com.example.workus.approval.mapper;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.dto.RefListViewDto;
import com.example.workus.approval.dto.ReqListViewDto;
import com.example.workus.approval.dto.WaitListViewDto;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalMapper {

    List<ApprovalCategory> getAllCategories();

    void insertApprovalFormBase(@Param("apvFormBase") ApvApprovalForm apvFormBase);

    List<User> getUsersByRoleNo(@Param("roleNo") int roleNo);

    void insertApprovalFormOption(@Param("termName") String termName
            , @Param("termValue") String termValue
            , @Param("apvNo") Long apvNo);

    List<ReqListViewDto> getReqList(@Param("userNo") Long userNo);

    List<ReqListViewDto> getWaitList();

    List<ReqListViewDto> getRefListByLeaderNo(@Param("leaderNo") Long leaderNo);

}
