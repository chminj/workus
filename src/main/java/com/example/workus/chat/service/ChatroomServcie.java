package com.example.workus.chat.service;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.chat.mapper.ChatroomMapper;
import com.example.workus.user.dto.DeptInChatroomDto;
import com.example.workus.user.dto.ParticipantInChatroomDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class ChatroomServcie {

    private final ChatroomMapper chatroomMapper;

    @Autowired
    public ChatroomServcie(ChatroomMapper chatroomMapper) {
        this.chatroomMapper = chatroomMapper;
    }

    /**
     * @return 로그인한 유저no가 참여중인 채팅방들
     */
    public List<ChatroomDto> getAllChatrooms(Long userNo) {
        List<Long> chatroomNumbers = chatroomMapper.getChatroomNoByUserNo(userNo);
        List<ChatroomDto> chatrooms = new ArrayList<>();
        for (Long chatroomNo : chatroomNumbers) {
            ChatroomDto chatroomDto = chatroomMapper.getChatRoomInMenuByChatroomNo(chatroomNo);
            chatroomDto.setNotReadCount(chatroomMapper.getNotReadCount(userNo, chatroomNo));
            chatrooms.add(chatroomDto);
        }

        return chatrooms;
    }

    public ChatroomInfoDto getChatroomInfo(Long chatroomNo) {
        return chatroomMapper.getChatroomInfoByChatroomNo(chatroomNo);
    }

    public void updateChatroomConTime(Long userNo, Long chatroomNo) {
        LocalDateTime now = LocalDateTime.now();
        chatroomMapper.updateChatroomConTime(userNo, chatroomNo, now);
    }

    public List<DeptInChatroomDto> getAllDepts() {
        return chatroomMapper.getAllDepts();
    }

    public Map<String, List<ParticipantInChatroomDto>> getAllUsersByDeptName(String deptName) {
        Map<String, List<ParticipantInChatroomDto>> map = new HashMap<>();
        List<ParticipantInChatroomDto> usersByDeptName = chatroomMapper.getAllUsersByDeptName(deptName);
        map.put(deptName, usersByDeptName);
        return map;
    }
}
