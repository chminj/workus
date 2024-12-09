package com.example.workus.approval.mapper;

import com.example.workus.approval.dto.ApvApprovalForm;
import com.example.workus.approval.dto.ApvDetailViewDto;
import com.example.workus.approval.dto.ApvListViewDto;
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

    List<ApvListViewDto> getReqList(@Param("userNo") Long userNo);

    List<ApvListViewDto> getWaitList();

    List<ApvListViewDto> getRefListByLeaderNo(@Param("leaderNo") Long leaderNo);

    ApvDetailViewDto getReqDetailByApvNo(@Param("apvNo") Long apvNo);
}
