package com.example.workus.calendar.mapper;

import com.example.workus.calendar.vo.Calendar;
import com.example.workus.security.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CalendarMapper {

    void insertCalendar(@Param("calendar") Calendar calendar);

    List<Calendar> selectEventsByDateRange(
            @Param("start") LocalDateTime startDateTime,
            @Param("end") LocalDateTime endDateTime,
            @Param("loginUser") LoginUser loginUser);








}
