package com.example.workus.community.controller;

import com.example.workus.community.dto.FeedForm;
import com.example.workus.community.service.CommunityService;
import com.example.workus.community.vo.Feed;
import com.example.workus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
    public List<Feed> getFeeds(@RequestParam(name ="page", required = false, defaultValue = "1") int page){
        List<Feed> feeds = communityService.getFeeds(page);
        return feeds;
    }

    @GetMapping("form")
    public String form(){
       return "community/form";
    }

    @PostMapping("/add")
    public String addFeed(FeedForm form){
        Long userNo = 20133L;

        communityService.addNewFeed(form, userNo);
        return "redirect:/community/list";
   }
}
