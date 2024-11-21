package com.example.workus.community.mapper;

import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    // 커뮤니티 게시글 조회
    List<Feed> getFeeds(@Param("begin") int begin, @Param("end") int end);
    List<HashTag> getHashTags(@Param("feedNo") long feedNo);
    int getTotalRows();

    // 게시글 작성
    void addNewFeed(@Param("feed") Feed feed);

    void addHashTag(@Param("hashTag") HashTag hashTag);
}
