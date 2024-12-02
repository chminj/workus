package com.example.workus.chat.mapper;

import com.example.workus.chat.vo.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {

    /**
     * chat 객체를 받아서 그 날 첫 번째 채팅인지 확인한다.
     * @param chat 객체
     * @return 'Y' 혹은 'N'
     */
    Character checkDailyFirstChat(@Param("chat") Chat chat);

    /**
     * 채팅방 번호를 받아서 방번호에 해당하는 채팅들을 출력한다.
     * @param chatroomNo 채팅방 번호
     * @return 채팅 리스트
     */
    List<Chat> getAllChatsByChatroomNo(@Param("userNo") Long userNo,
                                       @Param("chatroomNo") Long chatroomNo,
                                       @Param("begin") int begin);

    /**
     * 채팅 번호로 채팅 객체를 반환한다.
     * @param chatNo 채팅 번호
     * @return 채팅 1개 객체
     */
    Chat getChatByChatNo(@Param("chatNo") Long chatNo);

    /**
     * 채팅 객체를 받아서 insert한다.
     * @param chat 객체
     */
    void insertChat(@Param("chat") Chat chat);

    /**
     * 채팅 총 갯수를 가져온다.
     * @param userNo 로그인한 유저 번호
     * @param chatroomNo 채팅 방 번호
     * @return 채팅 총 갯수
     */
    int getTotalRows(@Param("userNo") Long userNo, @Param("chatroomNo") Long chatroomNo);
}
