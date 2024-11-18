package com.example.workus.chat.service;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.chat.mapper.ChatroomMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            chatrooms.add(chatroomMapper.getChatRoomInMenuByChatroomNo(chatroomNo));
        }

        return chatrooms;
    }

    public ChatroomInfoDto getChatroomInfo(Long chatroomNo) {
        return chatroomMapper.getChatroomInfoByChatroomNo(chatroomNo);
    }
}
