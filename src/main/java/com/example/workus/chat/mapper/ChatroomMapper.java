package com.example.workus.chat.mapper;

import com.example.workus.chat.dto.ChatroomDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatroomMapper {

    /**
     * 로그인한 유저no를 통해 채팅방 번호들을 불러온다.
     * @param userNo 로그인 유저no
     * @return 채팅방 번호
     */
    List<Integer> getChatroomNoByUserNo(@Param("userNo") int userNo);

    /**
     * 채팅방 번호를 받아서 채팅방 list에 들어가기 위한 dto를 반환한다.
     * @param chatroomNo
     * @return list에 보여줄 dto
     */
    ChatroomDto getChatRoom(@Param("chatroomNo") int chatroomNo);
}
