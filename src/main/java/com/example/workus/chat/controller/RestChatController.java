package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatForm;
import com.example.workus.chat.dto.RestResponseDto;
import com.example.workus.chat.service.ChatService;
import com.example.workus.chat.vo.Chat;
import com.example.workus.chat.vo.Chatroom;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ajax")
public class RestChatController {

    private final ChatService chatService;

    @Autowired
    public RestChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{chatroomNo}")
    ResponseEntity<RestResponseDto<List<Chat>>> getAllChats(@PathVariable("chatroomNo") Long chatroomNo) {
        List<Chat> chats = chatService.getAllChatsByChatroomNo(chatroomNo);
        return ResponseEntity.ok(RestResponseDto.success(chats));
    }

    @PostMapping("/chat/{chatroomNo}")
    ResponseEntity<RestResponseDto<Chat>> insertChat(
        @PathVariable("chatroomNo") Long chatroomNo
        , @ModelAttribute ChatForm chatForm) {
        // Chat객체에 chatroomNo와 폼에서 보낸 값을 insert한다.
        // insert한 후 chatNo로 chat객체를 반환한다.
        Chat chat = new Chat();
        Chatroom chatroom = new Chatroom();
        chatroom.setNo(chatroomNo);
        chat.setChatroom(chatroom);
        chat.setContent(chatForm.getContent());
        User user = new User();
        user.setNo(20140L);
        chat.setUser(user);
        return ResponseEntity.ok(RestResponseDto.success(chatService.insertChat(chat)));
    }
}
