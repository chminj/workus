package com.example.workus.common.controller;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.service.ChatroomServcie;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ChatroomServcie chatroomServcie;

    @Autowired
    public HomeController(ChatroomServcie chatroomServcie) {
        this.chatroomServcie = chatroomServcie;
    }

    @GetMapping("/home")
    public String home(Model model,
                       @AuthenticationPrincipal LoginUser loginUser) {
        ChatroomDto chatroomDto = chatroomServcie.getChatroomDtoByUserNo(loginUser.getNo());
        model.addAttribute("chatroomDto", chatroomDto);
        return "home";
    }
}
