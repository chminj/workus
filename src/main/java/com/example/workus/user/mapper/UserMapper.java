package com.example.workus.user.mapper;

import com.example.workus.user.dto.DeptDto;
import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();

    User getUserByUserNo(@Param("userNo") long userNo);

    User getUserById(@Param("id") String id);

    void insertUser(User user);

    List<User> getAllUsersByName(@Param("name") String userName);

    void updateSignUpUser(@Param("user") User user); // 사이트 회원가입 진행

    int getTotalRows(@Param("condition") Map<String, Object> condition); // 검색필터와 일치하는 회원수 조회

    List<User> getUsersByCondition(@Param("condition") Map<String, Object> condition); // 조건에 맞는 회원 정보 조회

    List<DeptDto> getAllDepts();

    void updateMyUser(@Param("user") User user); // 내 정보를 수정한다.

    List<DeptDto> getDeptByUserNo(@Param("userNo") long userNo);
    
    void updateMyPhone(@Param("user") User user); // 내 연락처를 수정한다.
    
    void updateMyPassword(@Param("user") User user); // 내 비밀번호를 수정한다.
}
