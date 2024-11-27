package com.example.workus.meeting.mapper;

import com.example.workus.meeting.vo.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MeetingMapper {

    void insertMeeting(@Param("meeting") Meeting meeting);
}
