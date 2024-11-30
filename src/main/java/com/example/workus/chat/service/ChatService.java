package com.example.workus.chat.service;

import com.example.workus.chat.mapper.ChatMapper;
import com.example.workus.chat.vo.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<Chat> getAllChatsByChatroomNo(Long userNo, Long chatroomNo) {
        return chatMapper.getAllChatsByChatroomNo(userNo, chatroomNo);
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
