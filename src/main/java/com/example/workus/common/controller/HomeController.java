package com.example.workus.common.controller;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.service.ChatroomServcie;
import com.example.workus.community.service.CommunityService;
import com.example.workus.community.vo.Feed;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ChatroomServcie chatroomServcie;

    private final CommunityService communityService;

    @Autowired
    public HomeController(ChatroomServcie chatroomServcie, CommunityService communityService) {
        this.chatroomServcie = chatroomServcie;
        this.communityService = communityService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String home(Model model,
                       @AuthenticationPrincipal LoginUser loginUser) {
        ChatroomDto chatroomDto = chatroomServcie.getChatroomDtoByUserNo(loginUser.getNo());
        model.addAttribute("chatroomDto", chatroomDto);
        Feed feed = communityService.getLastFeed();
        model.addAttribute("feed", feed); // JSP로 전달할 데이터 추가
        return "home";
    }
}
