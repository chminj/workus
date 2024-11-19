package com.example.workus.calendar.mapper;

import com.example.workus.calendar.vo.Calendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CalendarMapper {

    void insertCalendar(@Param("calendar") Calendar calendar);

}
