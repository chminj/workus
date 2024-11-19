package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.service.ChatroomServcie;
import com.example.workus.security.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    private final ChatroomServcie chatroomServcie;

    @Autowired
    public ChatroomController(ChatroomServcie chatroomServcie) {
        this.chatroomServcie = chatroomServcie;
    }

    @GetMapping("/list")
    public String chatroom(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        log.info("로그인 유저번호: " + loginUser.getNo());
        List<ChatroomDto> chatrooms = chatroomServcie.getAllChatrooms(20133L);
        model.addAttribute("chatrooms", chatrooms);
        return "chat/chatroom";
    }
}