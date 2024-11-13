package com.example.workus.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("list")
    public String list() {
        return "notice/list";
    }

    @GetMapping("detail")
    public String detail() {
        return "notice/detail";
    }

    @GetMapping("form")
    public String form(){
        return "notice/form";
    }

  @GetMapping("modify")
    public String modify() {
        return "notice/modify";
  }

}
