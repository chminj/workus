package com.example.workus.archive.controller;

import com.example.workus.archive.dto.ArchiveForm;
import com.example.workus.archive.service.ArchiveService;
import com.example.workus.archive.vo.Archive;
import com.example.workus.common.dto.ListDto;
import com.example.workus.common.util.FileUtils;
import com.example.workus.common.util.WebContentFileUtils;
import com.example.workus.security.LoginUser;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/archive")
public class ArchiveController {

    @Value("${upload.directory.archive}")
    private String saveDirectory;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("archiveList", archiveService.getArchiveList());
        return "archive/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public String upload(ArchiveForm form, @AuthenticationPrincipal LoginUser loginUser) {

        Archive archive = new Archive();

        archive.setUploadTime(java.sql.Timestamp.valueOf(LocalDateTime.now())); // 업로드 시간 설정
        archive.setFileStatus('A'); // 기본 활성화 상태
        archive.setDivision('P'); // 기본 개인 저장소로 설정

        User user = userMapper.getUserByUserNo(loginUser.getNo());
        archive.setUser(user);
        archive.setDeptNo(user.getDeptNo());

        MultipartFile multipartFile = form.getUpfile();
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
            String modifiedFilename = System.currentTimeMillis() + "_" + originalFilename;

            FileUtils.saveMultipartFile(multipartFile, saveDirectory, modifiedFilename);

            archive.setOriginalName(originalFilename);
            archive.setModifiedName(modifiedFilename);
            archive.setFileExtension(extension);
            archive.setFileSize((int) multipartFile.getSize());
        }

        archiveService.addNewFile(archive);

        return "redirect:list";
    }



}
