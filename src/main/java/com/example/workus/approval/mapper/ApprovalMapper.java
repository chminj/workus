package com.example.workus.approval.mapper;

import com.example.workus.approval.dto.ApprovalForm;
import com.example.workus.approval.vo.ApprovalCategory;
import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalMapper {

    List<ApprovalCategory> getAllCategories();

}
