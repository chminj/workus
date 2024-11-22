package com.example.workus.calendar.mapper;

import com.example.workus.calendar.vo.Calendar;
import com.example.workus.security.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CalendarMapper {

    Calendar selectCalendarByNo(Long calendarNo);
    List<Calendar> selectEventsByDateRange(
            @Param("start") LocalDateTime startDateTime,
            @Param("end") LocalDateTime endDateTime,
            @Param("division") List<Integer> division,
            @Param("loginUser") LoginUser loginUser);

    void insertCalendar(@Param("calendar") Calendar calendar);
    void deleteCalendar(@Param("eventId") Long eventId);
    void updateCalendar(@Param("calendar") Calendar calendar);
}
