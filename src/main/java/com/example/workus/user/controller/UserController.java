package com.example.workus.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        System.out.println("Login page accessed");
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

    @GetMapping("/user/myinfo")
    public String myinfo() {
        return "user/myinfo-form";
    }
}
