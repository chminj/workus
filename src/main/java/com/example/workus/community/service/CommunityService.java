package com.example.workus.community.service;

import com.example.workus.community.mapper.CommunityMapper;
import com.example.workus.community.vo.Feed;
import com.example.workus.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    CommunityMapper communityMapper;

    public List<Feed> getFeeds(int page){
        int totalRows = communityMapper.getTotalRows();
        Pagination pagination = new Pagination(page, totalRows,2);

        if(pagination.getTotalPages() < page)
                return null;

        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        List<Feed> feeds = communityMapper.getFeeds(begin,end);

        return feeds;
    }


}