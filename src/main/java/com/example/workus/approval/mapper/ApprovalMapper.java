package com.example.workus.approval.mapper;

import com.example.workus.approval.vo.ApprovalCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalMapper {

    List<ApprovalCategory> getAllCategories();

}
