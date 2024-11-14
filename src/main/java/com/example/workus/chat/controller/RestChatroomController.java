package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.chat.dto.RestResponseDto;
import com.example.workus.chat.service.ChatroomServcie;
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
public class RestChatroomController {

    private final ChatroomServcie chatroomServcie;

    @Autowired
    public RestChatroomController(ChatroomServcie chatroomServcie) {
        this.chatroomServcie = chatroomServcie;
    }

    @GetMapping("/chatroom/{chatroomNo}")
    ResponseEntity<RestResponseDto<ChatroomInfoDto>> getChatroomInfo(@PathVariable("chatroomNo") long chatroomNo) {
        return ResponseEntity.ok(RestResponseDto.success(chatroomServcie.getChatroomInfo(chatroomNo)));
    }
}
