package com.example.workus.chat.service;

import com.example.workus.chat.mapper.ChatMapper;
import com.example.workus.chat.vo.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Chat> getAllChatsByChatroomNo(Long chatroomNo) {
        return chatMapper.getAllChatsByChatroomNo(chatroomNo);
    }

    // chat을 insert후에 no를 가져와서 Chat객체를 반환한다.
    public Chat insertChat(Chat chat) {
        chatMapper.insertChat(chat);
        return chatMapper.getChatByChatNo(chat.getNo());
    }
}
