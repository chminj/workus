package com.example.workus.community.controller;

import com.example.workus.common.dto.RestResponseDto;
import com.example.workus.common.util.WebContentFileUtils;
import com.example.workus.community.dto.CommentForm;
import com.example.workus.community.dto.FeedForm;
import com.example.workus.community.dto.ModifyFrom;
import com.example.workus.community.mapper.CommunityMapper;
import com.example.workus.community.service.CommunityService;
import com.example.workus.community.vo.Feed;
import com.example.workus.community.vo.HashTag;
import com.example.workus.community.vo.Reply;
import com.example.workus.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;
    @Autowired
    private WebContentFileUtils webContentFileUtils;

    @GetMapping("/list")
    public String list() {
        return "community/list";
    }

    @GetMapping("/items")
    @ResponseBody
    public List<Feed> getFeeds(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(name = "opt", required = false) String opt,
                               @RequestParam(name = "keyword", required = false) String keyword) {
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
    public String form() {
        return "community/form";
    }

    @PostMapping("/insertFeed")
    public String addFeed(FeedForm form, @AuthenticationPrincipal LoginUser loginUser) {
        Long userNo = loginUser.getNo();

        communityService.insertFeed(form, userNo);
        return "redirect:/community/list";
    }

    @PostMapping("/insertReply")
    @ResponseBody
    public Reply insertReply(CommentForm commentForm, @AuthenticationPrincipal LoginUser loginUser) {
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

    @PostMapping("deleteFeed")
    public String deleteFeed(long feedNo, @AuthenticationPrincipal LoginUser loginUser) {
        Feed feed = communityService.getFeed(feedNo);
        Long userNo = loginUser.getNo();
        communityService.deleteFeed(feedNo, userNo);
        return "redirect:/community/list";
    }

    @GetMapping("modify")
    public String modifyFeed(long feedNo, @AuthenticationPrincipal LoginUser loginUser, Model model) {
        Feed feed = communityService.getFeedByFeedNo(feedNo);
        feed.setHashTags(communityService.getHashTagByFeedNo(feedNo));
        model.addAttribute("feed", feed);

        return "community/modify";
    }


    @PostMapping("updateFeed")
    public String updateFeeed(ModifyFrom form, @AuthenticationPrincipal LoginUser loginUser) {
        Long userNo = loginUser.getNo();
        communityService.updateFeed(form,userNo);
        return "redirect:/community/list";
    }















}