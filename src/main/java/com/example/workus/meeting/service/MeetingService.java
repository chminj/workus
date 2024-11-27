package com.example.workus.meeting.service;

import com.example.workus.calendar.vo.Calendar;
import com.example.workus.meeting.dto.MeetingForm;
import com.example.workus.meeting.mapper.MeetingMapper;
import com.example.workus.meeting.vo.Meeting;
import com.example.workus.security.LoginUser;
import com.example.workus.common.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;

    public Meeting addNewMeeting(MeetingForm form) {
        Meeting meeting = new Meeting();
        meeting.setStartDate(DateTimeUtil.getLocalDateTime(form.getStartDate()));
        meeting.setEndDate(DateTimeUtil.getLocalDateTime(form.getEndDate()));
        meeting.setContent(form.getContent());
        meeting.setRoom(form.getRoom());

        meeting.setUserNo(form.getUserNo());

        meetingMapper.insertMeeting(meeting);

        return meeting;
    }

    public List<Meeting> getEventsByDateRange(Date start, Date end, LoginUser loginUser) {
        LocalDateTime startDateTime = DateTimeUtil.toLocalDateTime(start);
        LocalDateTime endDateTime = DateTimeUtil.toLocalDateTime(end);

        return meetingMapper.selectEventsByDateRange(startDateTime, endDateTime, loginUser);
    }

    public Meeting getMeetingByNo(Long meetingNo) {
        return meetingMapper.selectMeetingByNo(meetingNo);
    }

    public boolean deleteMeeting(Long eventId) {
        Meeting meeting = meetingMapper.selectMeetingByNo(eventId);
        if (meeting != null) {
            meetingMapper.deleteMeeting(eventId);
            return true;
        } else {
            return false;
        }
    }
}
