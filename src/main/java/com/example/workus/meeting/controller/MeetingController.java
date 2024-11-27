package com.example.workus.meeting.controller;

import com.example.workus.calendar.vo.Calendar;
import com.example.workus.meeting.dto.MeetingForm;
import com.example.workus.meeting.service.MeetingService;
import com.example.workus.meeting.vo.Meeting;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @GetMapping("/events")
    @ResponseBody
    public List<Meeting> events(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @AuthenticationPrincipal LoginUser loginUser) {

        List<Meeting> events = meetingService.getEventsByDateRange(start, end, loginUser);

        return events;
    }











}
