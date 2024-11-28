package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.chat.dto.CreatingChatroomDto;
import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.chat.service.ChatroomServcie;
import com.example.workus.security.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/chatroom/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> getChatroomInfo(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo) {
        chatroomServcie.updateChatroomConTime(loginUser.getNo(), chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.getChatroomInfo(chatroomNo)));
    }

    @PutMapping("/chatroom/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> updateChatroomInfo(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo) {
        chatroomServcie.updateChatroomConTime(loginUser.getNo(), chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(null));
    }

    @PostMapping("/chatroom")
    ResponseEntity<RestResponseDto<ChatroomDto>> addChatroom(
            @AuthenticationPrincipal LoginUser loginUser,
            @ModelAttribute CreatingChatroomDto creatingChatroomDto) {
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.addChatroom(loginUser.getNo(), creatingChatroomDto)));
    }

    @GetMapping("/chatroom/out/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> getChatroomOut(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo) {
        chatroomServcie.outChatroomByChatroomNo(loginUser.getNo(), chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(null));
    }
}
