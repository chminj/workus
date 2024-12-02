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
import java.util.Collections;

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
        if(pagination.getBegin() != 0) {
            dto = new ListDto<>(chatMapper.getAllChatsByChatroomNo(userNo, chatroomNo, pagination.getBegin() - 1), pagination);
            Collections.sort(dto.getData(), (data1, data2) ->
                    data1.getTime().compareTo(data2.getTime()));
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
}
