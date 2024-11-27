package com.example.workus.meeting.service;

import com.example.workus.meeting.dto.MeetingForm;
import com.example.workus.meeting.mapper.MeetingMapper;
import com.example.workus.meeting.vo.Meeting;
import com.example.workus.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
