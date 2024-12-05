package com.example.workus.chat.service;

import com.example.workus.chat.mapper.ChatMapper;
import com.example.workus.chat.vo.Chat;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ChatService {

    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public ListDto<Chat> getAllChatsByChatroomNo(Long userNo, Long chatroomNo, int page) {
        int totalRows = chatMapper.getTotalRows(userNo, chatroomNo);
        Pagination pagination = new Pagination(page, totalRows);
        ListDto<Chat> dto;
        // 채팅방이 생성되고 친 채팅이 존재할 때
        if(pagination.getBegin() != 0) {
            // 가장 첫 페이지일 때 -> 더보기 때 10개씩 하기 위해 첫 페이지인 경우는 11개를 보여준다.
            dto = new ListDto<>(chatMapper.getAllChatsByChatroomNo(userNo, chatroomNo, pagination.getBegin() - 1), pagination);
            Collections.sort(dto.getData(), (data1, data2) ->
                    data1.getTime().compareTo(data2.getTime()));
            LocalDateTime firstChatTime = dto.getData().getFirst().getTime();
            LocalDateTime lastChatTime = dto.getData().getLast().getTime();

            // 입장하는 유저들 리스트와 입장 시간을 리스트로 받는다.
            // begin이 0이면 현재 시간부터 <- 1부터 하니깐 첫 채팅을 치지 않으면 입장 퇴장 표시가 나타나지 않음
            // lastChatTime이 10개 중 가장 아래 있는 채팅 시간(최근 채팅)
            List<Chat> enterMessages = null;
            if (pagination.getBegin() == 1) {
                enterMessages = chatMapper.getAllEnterUserNamesByChatroomNoAndChatTime(chatroomNo, firstChatTime, LocalDateTime.now());
            } else {
                enterMessages = chatMapper.getAllEnterUserNamesByChatroomNoAndChatTime(chatroomNo, firstChatTime, lastChatTime);
            }
            List<String> enterUserNames = new ArrayList<>();
            while (enterMessages != null && !enterMessages.isEmpty()) {
                Chat prev = enterMessages.removeFirst();
                LocalDateTime prevTime = prev.getTime();
                // 채팅방에 한 번에 들어온 사람들을 리스트로 묶는다.
                if (!enterMessages.isEmpty() && prevTime.equals((enterMessages.getFirst().getTime()))) {
                    enterUserNames.add(prev.getUser().getName());
                    continue;
                }
                enterUserNames.add(prev.getUser().getName());
                prev.setContent(getEnterTextMessage(enterUserNames));
                enterUserNames.clear();
                prev.setType("message");
                dto.getData().add(prev);
            }

            // 퇴장하는 유저들 리스트와 퇴장 시간을 리스트로 받는다.
            List<Chat> outMessages = null;
            if (pagination.getBegin() == 1) {
                outMessages = chatMapper.getAllOutUserNameByChatroomNoAndChatTime(chatroomNo,firstChatTime, LocalDateTime.now());
            } else {
                outMessages = chatMapper.getAllOutUserNameByChatroomNoAndChatTime(chatroomNo,firstChatTime, lastChatTime);
            }
            while (enterMessages != null && !outMessages.isEmpty()) {
                Chat outMessage = outMessages.removeFirst();
                outMessage.setType("message");
                outMessage.setContent(outMessage.getUser().getName() + "님이 퇴장하셨습니다.");
                dto.getData().add(outMessage);
            }
            // 입장 퇴장 메시지를 넣고 다시 정렬
            Collections.sort(dto.getData(), (data1, data2) ->
                    data1.getTime().compareTo(data2.getTime()));
            
            // 채팅방이 생성되고 친 채팅이 없을 때
        } else {
            dto = new ListDto<>(Collections.emptyList(), pagination);
        }
        return dto;
    }

    public Chat insertChat(Chat chat) {
        chat.setTime(LocalDateTime.now());
        if (chatMapper.checkDailyFirstChat(chat).equals('N')) {
            chat.setIsFirst('Y');
        }
        chatMapper.insertChat(chat);
        return chat;
    }

    /**
     * 입장 표시를 위한 메시지들을 담아서 입장 표시 메시지를 반환
     * @param enterUserNames 한 번에 표시 할 (같은 시간에) 입장한 유저들
     * @return 입장 메시지
     */
    public String getEnterTextMessage(List<String> enterUserNames) {
        StringBuilder sb = new StringBuilder();
        int len = enterUserNames.size();
        // 입장한 사람이 두 명이상인 경우만 ", "추가
        // 한 명이거나 마지막 사람은 ,없애고 입장하셨습니다.
        if (len > 1) {
            for (int i = 0; i < len - 1; i++) {
                sb.append(enterUserNames.get(i) + ", ");
            }
        }
        sb.append(enterUserNames.getLast() + "님이 입장하셨습니다.");
        return sb.toString();
    }
}
