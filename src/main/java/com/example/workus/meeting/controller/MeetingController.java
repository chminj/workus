package com.example.workus.meeting.controller;

import com.example.workus.meeting.dto.MeetingForm;
import com.example.workus.meeting.service.MeetingService;
import com.example.workus.meeting.vo.Meeting;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/list")
    public String list(){ return "meeting/list"; }

    @PostMapping("/add")
    @ResponseBody
    private Meeting add(MeetingForm form, @AuthenticationPrincipal LoginUser loginUser) {

        form.setUserNo(loginUser.getNo());

        Meeting meeting = meetingService.addNewMeeting(form);

        return meeting;
    }











}
