package com.example.workus.chat.mapper;

import com.example.workus.chat.vo.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {

    /**
     * 채팅방 번호를 받아서 방번호에 해당하는 채팅들을 출력한다.
     * @param chatroomNo 채팅방 번호
     * @return 채팅 리스트
     */
    List<Chat> getAllChatsByChatroomNo(@Param("chatroomNo") long chatroomNo);
}
