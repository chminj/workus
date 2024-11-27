package com.example.workus.meeting.controller;

import com.example.workus.calendar.vo.Calendar;
import com.example.workus.meeting.dto.MeetingForm;
import com.example.workus.meeting.service.MeetingService;
import com.example.workus.meeting.vo.Meeting;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestParam("id") Long eventId) {
        // 이벤트 삭제 로직
        boolean success = meetingService.deleteMeeting(eventId);
        if (success) {
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
        }
    }

    @PostMapping("/detail")
    @ResponseBody
    public ResponseEntity<Meeting> getMeetingDetail(@RequestParam("id") Long meetingNo) {
        Meeting meeting = meetingService.getMeetingByNo(meetingNo);

        if (meeting != null) {
            return ResponseEntity.ok(meeting);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
