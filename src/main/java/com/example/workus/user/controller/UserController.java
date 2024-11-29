package com.example.workus.user.controller;

import com.example.workus.user.dto.MyModifyForm;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
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
    public String myinfo(@RequestParam(name = "userNo") int userNo, Model model) { // userNo는 반드시 존재해야 하는 값
        User user = userService.getUserByUserNo(userNo); // userNo로 사용자 정보를 조회한다.
        model.addAttribute("user", user); // 사용자 정보를 model에 담는다. (view에 전달)
        return "user/myinfo-modify"; // View 페이지를 요청한다. [ View 페이지는 모델에 담은 값으로 구성된다. ]
    }

    @PostMapping("/user/myinfo")
    public String changeMyInfo(@ModelAttribute("myModifyForm") MyModifyForm form, BindingResult errors) { // view에서 MyModifyForm에 데이터를 담는다.
        if (errors.hasErrors()) {
            System.out.println("바인딩 시 에러가 발생했습니다.");
        }
        log.info("입력된 사번은 : " + form.getNo());
        log.info("입력된 이메일은 : " + form.getEmail());
        log.info("입력된 비밀번호는 : " + form.getPassword());
        log.info("입력된 연락처는 : " + form.getPhone());
        log.info("입력된 자기소개는 : " + form.getPr());
        log.info("입력된 주소는 : " + form.getAddress());
        log.info("입력된 이미지는 : " + form.getImage().getOriginalFilename());

        userService.modifyMyUser(form); // Form에 담긴 데이터를 서비스에 전달한다.

        // GET 방식 URL로 localhost/user/myinfo?userNo=사번으로 redirect 하기.
        return "redirect:/user/myinfo?userNo=" + form.getNo(); // GET 방식으로 리다이렉트
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
