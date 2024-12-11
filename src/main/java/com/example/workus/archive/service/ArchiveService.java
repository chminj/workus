package com.example.workus.archive.service;

import com.example.workus.archive.mapper.ArchiveMapper;
import com.example.workus.archive.vo.Archive;
import com.example.workus.common.dto.saveFileForm;
import com.example.workus.common.s3.S3Service;
import com.example.workus.security.LoginUser;
import com.example.workus.user.mapper.UserMapper;
import com.example.workus.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArchiveService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String folder = "resources/files";

    private final S3Service s3Service;

    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private UserMapper userMapper;

    public List<Archive> getArchiveList() {
        return archiveMapper.selectArchiveList();
    }

    public void saveFile(saveFileForm form, LoginUser loginUser) {

        MultipartFile saveFile = form.getSaveFile();
        String filename = System.currentTimeMillis() + "_" + saveFile.getOriginalFilename();
        s3Service.uploadFile(saveFile, bucketName, folder, filename);

        Archive archive = new Archive();
        archive.setUploadTime(new Date());
        archive.setOriginalName(saveFile.getOriginalFilename());
        archive.setSavedFileName(filename);
        archive.setFileSize(saveFile.getSize());
        archive.setFileExtension(saveFile.getOriginalFilename().substring(saveFile.getOriginalFilename().lastIndexOf(".") + 1));
        archive.setFileStatus('A');
        archive.setDivision('P');

        User user = userMapper.getUserByUserNo(loginUser.getNo());
        archive.setUser(user);
        archive.setDeptNo(user.getDeptNo());

        archiveMapper.insertArchive(archive);

    }


}
