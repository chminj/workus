package com.example.workus.calendar.controller;

import com.example.workus.calendar.dto.CalendarForm;
import com.example.workus.calendar.service.CalendarService;
import com.example.workus.calendar.vo.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/add")
    @ResponseBody
    public Calendar add(CalendarForm form) {
        System.out.println(form);
        Calendar calendar = calendarService.addNewCalendar(form);

        return calendar;
    }

    // 일정 목록 페이지 반환
    @GetMapping("/list")
    public String list(Model model) {

        return "calendar/list";  // calendar/list.jsp로 이동
    }


}
