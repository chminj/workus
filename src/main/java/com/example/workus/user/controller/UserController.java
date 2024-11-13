package com.example.workus.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "user/login-form";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup-form";
    }

    @GetMapping("/findpw")
    public String findpw() {
        return "user/findpw-form";
    }

    @GetMapping("/myinfo")
    public String myinfo() {
        return "user/myinfo-form";
    }
}
