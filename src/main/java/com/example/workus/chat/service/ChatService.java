package com.example.workus.chat.service;

import com.example.workus.chat.mapper.ChatMapper;
import com.example.workus.chat.vo.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public List<Chat> getAllChatsByChatroomNo(long chatroomNo) {
        return chatMapper.getAllChatsByChatroomNo(chatroomNo);
    }
}
