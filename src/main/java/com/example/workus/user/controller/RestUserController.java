package com.example.workus.user.controller;

import com.example.workus.chat.dto.RestResponseDto;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ajax")
public class RestUserController {

    private UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/chat/profile/{userNo}")
    ResponseEntity<RestResponseDto<User>> getProfileByUserNo(@PathVariable("userNo") Long userNo) {
        return ResponseEntity.ok(RestResponseDto.success(userService.getUserByUserNo(userNo)));
    }
}
