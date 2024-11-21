package com.example.workus.calendar.controller;

import com.example.workus.calendar.dto.CalendarForm;
import com.example.workus.calendar.service.CalendarService;
import com.example.workus.calendar.vo.Calendar;
import com.example.workus.security.LoginUser;
import com.example.workus.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/add")
    @ResponseBody
    public Calendar add(CalendarForm form, @AuthenticationPrincipal LoginUser loginUser) {

        form.setDeptNo(1003L);
        form.setNo(loginUser.getNo());

        Calendar calendar = calendarService.addNewCalendar(form);

        return calendar;
    }

    @PostMapping("/detail")
    @ResponseBody
    public ResponseEntity<Calendar> getCalendarDetail(@RequestParam("id") Long calendarNo) {
        Calendar calendar = calendarService.getCalendarByNo(calendarNo);

        if (calendar != null) {
            return ResponseEntity.ok(calendar);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/list")
    public String list() {
        return "calendar/list";
    }

    @GetMapping("/events")
    @ResponseBody
    public List<Calendar> events(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam("division") List<Integer> division,
            @AuthenticationPrincipal LoginUser loginUser) {

        List<Calendar> events = calendarService.getEventsByDateRange(start, end, division, loginUser);

        return events;
    }




}
