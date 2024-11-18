package com.example.workus.user.service;

import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String id, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setId(id);
        user.setPassword(encodedPassword);

        userMapper.insertUser(user);
    }

    public User getUserByUserNo(long userNo) {
        return userMapper.getUserByUserNo(userNo);
    }
}
