package com.example.workus.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    @GetMapping("/list")
    public String chatroom() {
        return "chat/chatroom";
    }
}
