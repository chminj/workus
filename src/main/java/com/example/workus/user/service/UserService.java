package com.example.workus.user.service;

import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 유저 전체를 조회한다.
     * @return 유저 정보 전체 리스트
     */
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

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

    /**
     * 사번으로 해당 사원이 존재하는지 확인한다.
     * @param userNo 사번
     * @return 해당 사원이 존재하면 true, 존재하지 않으면 false
     */
    public boolean isUserExistByUserNo(long userNo) {
        return userMapper.getUserByUserNo(userNo) != null; // 조회해왔는데 null 아니면 해당 유저가 존재하는 것이므로
    }

    /**
     * 아이디로 일치하는 사원이 존재하는지 확인한다.
     * @param userId 유저 아이디
     * @return 해당 아이디를 가진 사원이 존재하면 true, 존재하지 않으면 false
     */
    public boolean isUserExistByUserId(String userId) {
        return userMapper.getUserById(userId) != null; // 조회해왔는데 null이 아니면 해당 유저가 존재하는 것이므로
    }
}
