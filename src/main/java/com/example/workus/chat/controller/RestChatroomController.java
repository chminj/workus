package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomForm;
import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.chat.service.ChatroomServcie;
import com.example.workus.security.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ajax")
public class RestChatroomController {

    private final ChatroomServcie chatroomServcie;

    @Autowired
    public RestChatroomController(ChatroomServcie chatroomServcie) {
        this.chatroomServcie = chatroomServcie;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chatroom/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> getChatroomInfoAndUpdateConTimeByChatroomNo(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo) {
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.getChatroomInfo(chatroomNo)));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/chatroom/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> updateContimeByChatroomNo(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo) {
        chatroomServcie.updateChatroomConTime(loginUser.getNo(), chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(null));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/chatroom")
    ResponseEntity<RestResponseDto<ChatroomDto>> addChatroom(
            @AuthenticationPrincipal LoginUser loginUser,
            @ModelAttribute ChatroomForm chatroomForm) {
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.addChatroom(loginUser.getNo(), chatroomForm)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("chatroom/invited/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> getChatroomInfoByChatroomNo(
            @PathVariable("chatroomNo") Long chatroomNo) {
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.getChatroomInfoByChatroomNo(chatroomNo)));
    }
}
