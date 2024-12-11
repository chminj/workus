package com.example.workus.archive.controller;

import com.example.workus.archive.service.ArchiveService;
import com.example.workus.archive.view.FileDownloadView;
import com.example.workus.common.dto.DownloadFileData;
import com.example.workus.common.dto.saveFileForm;
import com.example.workus.security.LoginUser;
import com.example.workus.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("no") Long no, @AuthenticationPrincipal LoginUser loginUser) {
        DownloadFileData fileData = archiveService.downloadFile(no, loginUser);

        if (fileData == null || fileData.getResource() == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }

        try {
            String filename = fileData.getFilename();
            String encodedFileName = new String(filename.getBytes("UTF-8"), "ISO-8859-1");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fileData.getResource().contentLength())
                    .body(fileData.getResource());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("파일 이름 인코딩 실패", e);
        }

    }








}
