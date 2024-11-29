package com.example.workus.community.service;

import com.example.workus.community.dto.CommentForm;
import com.example.workus.community.dto.FeedForm;
import com.example.workus.community.mapper.CommunityMapper;
import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.HashTag;
import com.example.workus.community.vo.Reply;
import com.example.workus.user.vo.User;
import com.example.workus.common.util.FileUtils;
import com.example.workus.common.util.Pagination;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class CommunityService {

    @Autowired
    CommunityMapper communityMapper;

    // 게시글 조회 무한스크롤
    public List<Feed> getFeeds(int page){
        int totalRows = communityMapper.getTotalRows();
        Pagination pagination = new Pagination(page, totalRows,2);

        if(pagination.getTotalPages() < page)
                return null;
        int begin = pagination.getBegin();
        int end = pagination.getEnd();
        List<Feed> feeds = communityMapper.getFeeds(begin,end);
        for (Feed feed : feeds) {
            List<HashTag> hashTags = communityMapper.getHashTagsByFeedNo(feed.getNo());
            feed.setHashTags(hashTags);
            Reply reply = communityMapper.getReplyByFeedNo(feed.getNo());
            feed.setReply(reply);
        }

        return feeds;
    }

    // 게시글 작성 (제목,내용,해쉬태그,파일)
    public void insertFeed(FeedForm form, Long userNo){
        MultipartFile multipartFile = form.getUpfile();
        // 멀티파트 파일에 진짜 이름을 가져옴
        String originalFilename = multipartFile.getOriginalFilename();
        // 경로지정
        String saveDirectory = "C:\\projects\\final-workspace\\src\\main\\webapp\\resources\\images";
        // 첨부파일, 디렉토리 경로, 저장할 파일명을 전달받아서 파일을 저장한다.
        FileUtils.saveMultipartFile(multipartFile,saveDirectory,originalFilename);

        // 게시글 생성
        Feed feed = new Feed();
        // 입력한 내용 게시글에 전달
        feed.setTitle(form.getTitle());
        feed.setContent(form.getContent());
        feed.setUser(User.builder().no(userNo).build());
        feed.setMediaUrl(originalFilename);
        feed.setReply(new Reply());
        // feed -> {no:0, title:'xxxx'}

        System.out.println(feed.getNo());
        // 인서트 sql 내용에 데이터 넣음
        communityMapper.insertFeed(feed);
        System.out.println(feed.getNo());
        // feed -> {no:34}

        try {
            // jsonText = [{"value":"#222"},{"value":"#333"}]
            String jsonText = form.getTags();
            ObjectMapper mapper = new ObjectMapper();

            // list ---> [map, map]
            // map -> {key:"value" value:"#222"}
            // map -> {key:"value" value:"#333"} 이렇게 담겨 있다.
            List<Map<String, String>> list = mapper.readValue(jsonText, new TypeReference<List<Map<String, String>>>() {});

            // 반복문 사용해서 객체 데이터 뽑아서 담음
            for (Map<String, String> map : list) {
                String value = map.get("value");

                HashTag hashTag = new HashTag();
                hashTag.setFeed(feed);
                hashTag.setName(value);

                communityMapper.insertHashTag(hashTag);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Reply insertReply(CommentForm form, Long userNo) {
        // 댓글 객체 생성
        Reply reply = new Reply();
        reply.setContent(form.getComment());  // 댓글 내용

        // 피드 객체 생성
        Feed feed = new Feed();
        feed.setNo(form.getFeedNo());
        reply.setFeed(feed);

        // 사용자 객체 생성
        User user = new User();
        user.setNo(userNo);
        reply.setUser(user);

        // 댓글 저장
        communityMapper.insertReply(reply);

        // 최신 리플 조회
        reply = communityMapper.getReplyByFeedNo(form.getFeedNo());

        // 저장된 댓글 반환
        return reply;
    }

    public Feed getFeed(long feedNo) {
        Feed feed = communityMapper.getFeedByNo(feedNo);
        List<Reply> replys = communityMapper.getReplysByFeedNo(feedNo);
        feed.setReplys(replys);
        return feed;
    }


}