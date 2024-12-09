package com.example.workus.chat.controller;

import com.example.workus.chat.dto.ChatForm;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.chat.service.ChatService;
import com.example.workus.chat.vo.Chat;
import com.example.workus.chat.vo.Chatroom;
import com.example.workus.common.s3.S3Service;
import com.example.workus.security.LoginUser;
import com.example.workus.user.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/ajax")
public class RestChatController {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    private static final String CHAT_DIR = "/chat";

    private final ChatService chatService;
    private final S3Service s3Service;

    @Autowired
    public RestChatController(ChatService chatService, S3Service s3Service) {
        this.chatService = chatService;
        this.s3Service = s3Service;
    }

    @GetMapping("/chat/{page}/{chatroomNo}")
    ResponseEntity<RestResponseDto<ListDto<Chat>>> getAllChats(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo,
            @PathVariable("page") int page) {
        return ResponseEntity.ok(RestResponseDto.success(chatService.getAllChatsByChatroomNo(loginUser.getNo(), chatroomNo, page)));
    }

    @PostMapping("/chat/{chatroomNo}")
    ResponseEntity<RestResponseDto<Chat>> insertChat(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("chatroomNo") Long chatroomNo,
            @ModelAttribute ChatForm chatForm) {
        Chat chat = new Chat();
        Chatroom chatroom = new Chatroom();
        chatroom.setNo(chatroomNo);
        chat.setChatroom(chatroom);
        chat.setContent(chatForm.getContent());
        User user = new User();
        user.setNo(loginUser.getNo());
        chat.setUser(user);
        return ResponseEntity.ok(RestResponseDto.success(chatService.insertChat(chat)));
    }

    @PostMapping("/chat/upload")
    ResponseEntity<RestResponseDto<Chat>> uploadFile(@ModelAttribute ChatForm chatForm) {
        MultipartFile file = chatForm.getUpfile();
        String originalFilename = file.getOriginalFilename();
        String filename = System.currentTimeMillis() + originalFilename;
        s3Service.uploadFile(file, bucketName, folder + CHAT_DIR, filename);
        Chat chat = new Chat();
        chat.setFileSrc(filename);
        return ResponseEntity.ok(RestResponseDto.success(chat));
    }
}
