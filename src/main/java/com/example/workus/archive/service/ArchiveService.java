package com.example.workus.archive.service;

import com.example.workus.archive.mapper.ArchiveMapper;
import com.example.workus.archive.vo.Archive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;

    public void addNewFile(Archive archive) {
        archiveMapper.insertArchive(archive);
    }

    public List<Archive> getArchiveList() {
        return archiveMapper.selectArchiveList();
    }
}
