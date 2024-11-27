package com.example.workus.common.websocket;

import com.example.workus.chat.vo.Chat;
import com.example.workus.user.vo.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ChatMessage {

    // 채팅 명령
    // chat-open : 채팅 연결 요청
    // chat-close : 채팅 연결 종료 요청
    // chat-message : 채팅 전송
    // chat-open-success : 채팅 연결 성공
    // chat-close-success : 채팅 연결 종료 성공
    private String cmd;
    private Long chatroomNo;
    private User user;
    private String text;
    private Chat chat;
    private Set<String> onlineUserIds; // 채팅방에 현재 참여중인 유저 번호들
}
