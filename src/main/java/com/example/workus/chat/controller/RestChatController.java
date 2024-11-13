package com.example.workus.chat.controller;

import com.example.workus.chat.dto.RestResponseDto;
import com.example.workus.chat.service.ChatService;
import com.example.workus.chat.vo.Chat;
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
public class RestChatController {

    private final ChatService chatService;

    @Autowired
    public RestChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{chatroomNo}")
    ResponseEntity<RestResponseDto<List<Chat>>> getAllChats(@PathVariable("chatroomNo") Long chatroomNo) {
        List<Chat> chats = chatService.getAllChatsByChatroomNo(chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(chats));
    }
}
