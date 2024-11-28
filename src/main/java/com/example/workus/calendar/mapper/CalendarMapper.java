package com.example.workus.calendar.mapper;

import com.example.workus.calendar.vo.Calendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CalendarMapper {

    Calendar selectCalendarByNo(@Param("calendarNo") Long calendarNo, @Param("userNo") Long userNo);
    List<Calendar> selectTeamAndPersonalEvents(
            @Param("userNo") long userNo,
            @Param("deptNo") long deptNo,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("division") List<Integer> division
    );

    void insertCalendar(@Param("calendar") Calendar calendar);
    void deleteCalendar(@Param("eventId") Long eventId);
    void updateCalendar(@Param("calendar") Calendar calendar);

    Calendar selectCalendarByNoAndUser(@Param("calendarNo") long no, @Param("userNo") Long userNo);
}
