package com.example.workus.user.controller;

import com.example.workus.user.dto.UserSignUpForm;
import com.example.workus.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginform() {
        return "user/login-form";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new UserSignUpForm());
        return "user/signup-form";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm")UserSignUpForm form, BindingResult errors) {

        if (errors.hasErrors()) {
            return "user/signup-form";
        }

        if(!form.getPassword().equals(form.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "passwordConfirm.invalid");
            return "user/signup-form";
        }

        userService.addSignUpUser(form); // Form에 담긴 데이터를 서비스에 전달한다.

        return "redirect:/login"; // 회원 가입 완료되면 로그인 페이지로 이동한다.
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
