package com.example.workus.chat.mapper;

import com.example.workus.chat.dto.ChatroomDto;
import com.example.workus.chat.dto.ChatroomInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChatroomMapper {

    /**
     * 로그인한 유저no를 통해 채팅방 번호들을 불러온다.
     * @param userNo 로그인 유저no
     * @return 채팅방 번호
     */
    List<Long> getChatroomNoByUserNo(@Param("userNo") Long userNo);

    /**
     * 채팅방 번호를 받아서 채팅방 list에 들어가기 위한 dto를 반환한다.
     * @param chatroomNo
     * @return list에 보여줄 dto
     */
    ChatroomDto getChatRoomInMenuByChatroomNo(@Param("chatroomNo") Long chatroomNo);

    /**
     * 채팅방 번호를 받아서 채팅방 제목과 그 방에 참여중인 유저들 목록을 불러온다.
     * @param chatroomNo
     * @return 채팅방 제목과 방에 참여중인 유저들
     */
    ChatroomInfoDto getChatroomInfoByChatroomNo(@Param("chatroomNo") Long chatroomNo);

    /**
     * 채팅방 번호를 받아서 최근 접속 시간을 수정한다.
     * @param userNo, chatroomNo, no
     */
    void updateChatroomConTime(@Param("userNo") Long userNo, @Param("chatroomNo") Long chatroomNo, @Param("now") LocalDateTime now);

    /**
     * 채팅방 번호와 유저 번호를 받아서 읽지 않은 메시지 수를 반환한다.
     * @param chatroomNo
     * @return 읽지 앟은 메시지 수
     */
    int getNotReadCount(@Param("userNo") Long userNo, @Param("chatroomNo") Long chatroomNo);
}
