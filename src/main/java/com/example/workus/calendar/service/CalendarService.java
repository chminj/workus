package com.example.workus.calendar.service;

import com.example.workus.calendar.dto.CalendarForm;
import com.example.workus.calendar.mapper.CalendarMapper;
import com.example.workus.calendar.vo.Calendar;
import com.example.workus.security.LoginUser;
import com.example.workus.common.util.DateTimeUtil;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;
    @Autowired
    private UserMapper userMapper;

    // 새 일정 추가
    public Calendar addNewCalendar(CalendarForm form) {
        User user = userMapper.getUserByUserNo(form.getUserNo());

        Calendar calendar = new Calendar();
        calendar.setName(form.getName());
        calendar.setLocation(form.getLocation());
        calendar.setStartDate(DateTimeUtil.getLocalDateTime(form.getStartDate()));
        calendar.setEndDate(DateTimeUtil.getLocalDateTime(form.getEndDate()));
        calendar.setDivision(form.getDivision());
        calendar.setContent(form.getContent());

        calendar.setUserNo(user.getNo());
        calendar.setDeptNo(user.getDeptNo());

        calendarMapper.insertCalendar(calendar);
        return calendar;
    }

    // 기간 및 구분별 일정 조회
    public List<Calendar> getTeamAndPersonalEvents(Date start, Date end, List<Integer> division, LoginUser loginUser) {
        // 로그인 사용자 정보 조회
        User user = userMapper.getUserByUserNo(loginUser.getNo());

        // 날짜 범위 변환
        LocalDateTime startDateTime = DateTimeUtil.toLocalDateTime(start);
        LocalDateTime endDateTime = DateTimeUtil.toLocalDateTime(end);

        // 개인 및 팀 일정 조회
        return calendarMapper.selectTeamAndPersonalEvents(user.getNo(), user.getDeptNo(), startDateTime, endDateTime, division);
    }

    // 특정 일정 조회
    public Calendar getCalendarByNo(Long calendarNo, Long userNo) {
        return calendarMapper.selectCalendarByNo(calendarNo, userNo);
    }

    // 일정 수정
    public void updateCalendar(Calendar calendar, Long userNo) {
        Calendar existingCalendar = calendarMapper.selectCalendarByNoAndUser(calendar.getNo(), userNo);
        if (existingCalendar != null) {
            calendarMapper.updateCalendar(calendar);
        } else {
            throw new RuntimeException("수정할 권한이 없습니다.");
        }
    }

    // 일정 삭제
    public boolean deleteCalendar(Long eventId, Long userNo) {
        Calendar calendar = calendarMapper.selectCalendarByNoAndUser(eventId, userNo);
        if (calendar != null) {
            calendarMapper.deleteCalendar(eventId);
            return true;
        } else {
            return false;
        }
    }
}
