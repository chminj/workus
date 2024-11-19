package com.example.workus.community.mapper;

import com.example.workus.community.vo.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<Feed> getFeeds(@Param("begin") int begin, @Param("end") int end);
    int getTotalRows();
}
