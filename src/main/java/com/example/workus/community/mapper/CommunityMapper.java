package com.example.workus.community.mapper;

import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.HashTag;
import com.example.workus.community.vo.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    //게시글 조회
    List<Feed> getFeeds(@Param("begin") int begin, @Param("end") int end);
    List<HashTag> getHashTagsByFeedNo(@Param("feedNo") long feedNo);
    int getTotalRows();

    // 게시글 작성
    void insertFeed(@Param("feed") Feed feed);
    void insertHashTag(@Param("hashTag") HashTag hashTag);

    // 댓글 작성
    void insertReply(@Param("reply") Reply reply);

    // 최신댓글 한개 조회
   Reply getReplyByFeedNo(@Param("feedNo") long feedNo);

}
