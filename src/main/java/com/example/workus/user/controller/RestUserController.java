package com.example.workus.user.controller;

import com.example.workus.chat.dto.RestResponseDto;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import com.example.workus.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/user/check-no/{userNo}")
    ResponseEntity<RestResponseDto<Boolean>> checkUserNo(@PathVariable("userNo") Long userNo) {
        boolean isUserExist = userService.isUserExistByUserNo(userNo);
        return ResponseEntity.ok(RestResponseDto.success(isUserExist)); // 유저가 존재하면 true
    }

    @GetMapping("/user/check-id/{userId}")
    ResponseEntity<RestResponseDto<Boolean>> checkUserId(@PathVariable("userId") String userId) {
        boolean isUserExist = userService.isUserExistByUserId(userId);
        return ResponseEntity.ok(RestResponseDto.success(isUserExist)); // 유저가 존재하면 true
    }

    @GetMapping("/chatroom/user/search/{userName}")
    ResponseEntity<RestResponseDto<List<User>>> getAllUsersByUserName(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(RestResponseDto.success(userService.getAllUsersByName(userName)));
    }
}
