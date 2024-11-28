package com.example.workus.user.controller;

import com.example.workus.user.dto.UserListDto;
import com.example.workus.user.dto.UserSignUpForm;
import com.example.workus.user.service.UserService;
import com.example.workus.user.vo.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {

    // 파일 경로를 담을 path 설정.
    private String path ="resources/repository/userprofile/";

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

    @GetMapping("/address-book/list")
    public String list(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "dept", required = false) String dept,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        log.info("페이지번호: {}", page);
        log.info("부서: {}", dept);
        log.info("직원명: {}", name);

        Map<String, Object> condition = new HashMap<>(); // 검색 조건을 담을 Map 객체
        condition.put("page", page); // 페이지 번호
        if (!"all".equals(dept)) {
            condition.put("dept", dept); // 부서 선택 옵션
        }
        if (StringUtils.hasText(name)) {
            condition.put("name", name); // 직원명
        }

        System.out.println("---------------------------" + condition);
        // 검색 조건으로 유저 목록을 조회해야 한다.
        UserListDto<User> dto = userService.getUserListByCondition(condition);
        System.out.println(dto.toString());
        System.out.println(dto.getData());
        // UserListDto<User>를 "users"로 모델에 저장한다.
        model.addAttribute("userList", dto.getData()); // 유저 목록

        System.out.println("-----------------------------------------" + dto.getPaging().toString());
        // Pagination을 "paging"으로 모델에 저장한다.
        model.addAttribute("paging", dto.getPaging()); // 페이지네이션 객체

        return "addressbook/list";
    }
}
