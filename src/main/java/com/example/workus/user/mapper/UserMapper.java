package com.example.workus.user.mapper;

import com.example.workus.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * user번호를 받아서 user객체를 반환한다.
     * @param userNo
     * @return 유저 모든 정보가 담긴 객체
     */
    User getUserByUserNo(@Param("userNo") long userNo);
}
