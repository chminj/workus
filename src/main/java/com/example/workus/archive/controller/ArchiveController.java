package com.example.workus.archive.controller;

import com.example.workus.archive.service.ArchiveService;
import com.example.workus.archive.view.FileDownloadView;
import com.example.workus.common.dto.saveFileForm;
import com.example.workus.security.LoginUser;
import com.example.workus.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/archive")
@RequiredArgsConstructor
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("archiveList", archiveService.getArchiveList());

        return "archive/list";
    }

    @PostMapping("/save")
    public String save(saveFileForm form, @AuthenticationPrincipal LoginUser loginUser) {
        archiveService.saveFile(form, loginUser);
        return "redirect:/archive/list";
    }






}
