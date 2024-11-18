package com.example.workus.security;

import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        // ID로 사용자 정보를 조회한다.
        User user = userMapper.getUserById(id);
        System.out.println("loadUserByUsername 실행됨");

        if(user == null) {
            System.out.println("사용자를 검색하지 못했습니다.");
           throw new UsernameNotFoundException("["+id+"]사용자가 없습니다.");
       }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return customUserDetails;
    }

}

