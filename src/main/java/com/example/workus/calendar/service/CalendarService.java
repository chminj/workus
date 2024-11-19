package com.example.workus.calendar.service;

import com.example.workus.calendar.dto.CalendarForm;
import com.example.workus.calendar.mapper.CalendarMapper;
import com.example.workus.calendar.vo.Calendar;
import com.example.workus.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;

    public Calendar addNewCalendar(CalendarForm form) {
        Calendar calendar = new Calendar();
        calendar.setName(form.getName());
        calendar.setLocation(form.getLocation());
        calendar.setStartDate(DateTimeUtil.getLocalDateTime(form.getStartDate()));
        calendar.setEndDate(DateTimeUtil.getLocalDateTime(form.getEndDate()));
        calendar.setDivision(form.getDivision());
        calendar.setContent(form.getContent());

        calendarMapper.insertCalendar(calendar);

        return calendar;
    }






}
