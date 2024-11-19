package com.example.workus.community.controller;

import com.example.workus.community.service.CommunityService;
import com.example.workus.community.vo.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @GetMapping("/form")
   public String form(){
       return "community/form";
   }
}
