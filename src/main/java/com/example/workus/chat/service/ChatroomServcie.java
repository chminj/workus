package com.example.workus.chat.service;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomInfoDto;
import com.example.workus.chat.dto.CreatingChatroomDto;
import com.example.workus.chat.mapper.ChatroomMapper;
import com.example.workus.chat.vo.Chatroom;
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
            if (chatroomDto != null) {
                chatroomDto.setNotReadCount(chatroomMapper.getNotReadCount(userNo, chatroomNo));
            } else {
                chatroomDto = new ChatroomDto();
                Chatroom chatroom = chatroomMapper.getChatroomByChatroomNo(chatroomNo);
                chatroomDto.setChatroomNo(chatroomNo);
                chatroomDto.setChatroomTitle(chatroom.getTitle());
            }
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

    // 채팅방 생성
    public ChatroomDto addChatroom(Long userNo, CreatingChatroomDto creatingChatroomDto) {
        // 채팅방 생성
        Chatroom chatroom = new Chatroom();
        String chatroomTitle = creatingChatroomDto.getChatroomTitle();
        chatroom.setUserNo(userNo);
        chatroom.setTitle(chatroomTitle);
        chatroomMapper.addChatroom(chatroom);

        // 채팅방 참여자들 번호 그릇 participantUserNumbers
        List<Long> participantUserNumbers = creatingChatroomDto.getUserNo();

        // disabled로 해놔서 작성자는 들어오지 않아서 따로 추가
        participantUserNumbers.add(userNo);

        // 채팅방 참여 히스토리에 추가
        Long chatroomNo = chatroom.getNo(); // 한 번만 참조하기 위해서 선언
        for(Long participantUserNo : participantUserNumbers) {
            chatroomMapper.addChatroomHistory(chatroomNo, participantUserNo);
        }

        // ajax로 채팅방을 추가로 보여주기 위해 ChatroomDto에 담아서 반환
        ChatroomDto chatroomDto = new ChatroomDto();
        chatroomDto.setChatroomNo(chatroomNo);
        chatroomDto.setChatroomTitle(chatroomTitle);
        return chatroomDto;
    }

    public void outChatroomByChatroomNo(Long userNo, Long chatroomNo) {
        chatroomMapper.outChatroomByChatroomNo(userNo, chatroomNo);
    }

    public ChatroomInfoDto getChatroomInfoByChatroomNo(Long chatroomNo) {
        return chatroomMapper.getChatroomInfoByChatroomNo(chatroomNo);
    }
}
