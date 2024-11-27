package com.example.workus.meeting.mapper;

import com.example.workus.meeting.vo.Meeting;
import com.example.workus.security.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MeetingMapper {

    Meeting selectMeetingByNo(Long meetingNo);
    List<Meeting> selectEventsByDateRange(
            @Param("start") LocalDateTime startDateTime,
            @Param("end") LocalDateTime endDateTime,
            @Param("loginUser") LoginUser loginUser);

    void insertMeeting(@Param("meeting") Meeting meeting);
    void deleteMeeting(@Param("eventId") Long eventId);
}
