package com.example.workus.user.service;

import com.example.workus.exception.WorkusException;
import com.example.workus.user.dto.UserSignUpForm;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import com.example.workus.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    /**
     * 회원가입을 통해 그룹웨어의 신규 회원을 등록한다.
     * @Param 회원가입 폼에 담긴 정보들
     */
    public void addSignUpUser(UserSignUpForm form) {
        User user = new User(); // 폼에 담긴 정보를 User 객체에 담자.
        user.setId(form.getId());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        // 비밀번호는 passwordEncoder에 의해 암호화된 비밀번호를 DB 저장
        user.setPhone(UserUtils.getFormatPhoneNumber(form.getPhone()));
        // 휴대폰 번호는 UserUtils를 통해 포맷팅해서 DB에 저장한다.
        user.setAddress(form.getAddress() + " " + form.getDetailAddress());
        // 주소는 API를 통해 입력된 주소 + 상세 주소
        user.setNo(form.getNo()); // 사번으로 조회해야 하니 사번을 전달한다.

        try {
            userMapper.updateSignUpUser(user);
        } catch (DataAccessException e) {
            throw new WorkusException("회원가입 처리 중 오류가 발생함" + e);
        }
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

    public List<User> getAllUsersByName(String userName) {
        return userMapper.getAllUsersByName(userName);
    }
}
