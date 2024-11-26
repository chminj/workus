package com.example.workus.user.mapper;

import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();
    User getUserByUserNo(@Param("userNo") long userNo);
    User getUserById(@Param("id") String id);
    void updateSignUpUser(@Param("user") User user); // 사이트 회원가입 진행
}
