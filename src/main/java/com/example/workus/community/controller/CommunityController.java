package com.example.workus.community.controller;

import com.example.workus.community.dto.CommentForm;
import com.example.workus.community.dto.FeedForm;
import com.example.workus.community.service.CommunityService;
import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.Reply;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @GetMapping("/list")
    public String list(){
        return "community/list";
    }

    @GetMapping("/items")
    @ResponseBody
    public List<Feed> getFeeds(@RequestParam(name ="page", required = false, defaultValue = "1") int page,
                               @RequestParam(name = "opt", required = false) String opt,
                               @RequestParam(name = "keyword", required = false) String keyword){
        Map<String, Object> condition = new HashMap<>();
        condition.put("page", page);

        if (StringUtils.hasText(opt) && StringUtils.hasText(keyword)) {
            condition.put("opt", opt);
            condition.put("keyword", keyword);
        }
        List<Feed> feeds = communityService.getFeeds(condition);
        return feeds;
    }

    @GetMapping("form")
    public String form(){
       return "community/form";
    }

    @PostMapping("/insertFeed")
    public String addFeed(FeedForm form ,@AuthenticationPrincipal LoginUser loginUser){
        Long userNo = loginUser.getNo();

        communityService.insertFeed(form, userNo);
        return "redirect:/community/list";
   }

   @PostMapping("/insertReply")
   @ResponseBody
    public Reply insertReply(CommentForm commentForm, @AuthenticationPrincipal LoginUser loginUser ){
       Long userNo = loginUser.getNo();
       Reply reply = communityService.insertReply(commentForm, userNo);

       return reply;
   }

    @GetMapping("/feed/{feedNo}")
    @ResponseBody
    public Feed getFeedDetail(@PathVariable Long feedNo) {
        Feed feed = communityService.getFeed(feedNo);
        return feed;
    }

}