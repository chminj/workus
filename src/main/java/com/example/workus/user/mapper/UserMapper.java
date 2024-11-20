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
    void insertUser(User user);
}
