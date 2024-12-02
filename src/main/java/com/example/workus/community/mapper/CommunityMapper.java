package com.example.workus.community.mapper;

import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.HashTag;
import com.example.workus.community.vo.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper {

    //게시글 조회
    List<Feed> getFeeds(@Param("begin") int begin, @Param("end") int end);
    List<HashTag> getHashTagsByFeedNo(@Param("feedNo") long feedNo);
    int getTotalRows();
    // 게시글 상세 조회
    Feed getFeedByNo(@Param("feedNo") long feedNo);
    // 게시글에 해당하는 모든 댓글 조회
    List<Reply> getReplysByFeedNo(@Param("feedNo") long feedNo);
    List<Feed> searchFeeds(Map<String, Object> condition);

    // 게시글 작성
    void insertFeed(@Param("feed") Feed feed);
    void insertHashTag(@Param("hashTag") HashTag hashTag);

    // 게시글 검색
    List<Feed> getSearchFeeds(@Param("condition")Map<String, Object> condition);
    int getTotalRows2(@Param("condition")Map<String, Object> condition);


    // 댓글 작성
    void insertReply(@Param("reply") Reply reply);
    // 최신댓글 한개 조회
    Reply getReplyByFeedNo(@Param("feedNo") long feedNo);


}
