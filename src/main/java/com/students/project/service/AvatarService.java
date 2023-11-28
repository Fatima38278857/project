package com.students.project.service;

import com.students.project.exception.StudentNotFoundException;
import com.students.project.model.Avatar;
import com.students.project.model.Student;
import com.students.project.repository.AvatarRepository;
import com.students.project.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    private  final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private final String avatarsDir;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository, @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.getById(studentId);
        logger.error("Аватар не добавлен");
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }


    private String getExtensions(String fileName) {
        logger.info("Был вызван getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);

    }

    public Avatar findAvatar(Long id) {
        logger.info("Был вызван findAvatar");
        return avatarRepository.findById(id).get();

    }

    public Page<Avatar> getListsAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Был вызван getListsAvatars");
        return avatarRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
    }


}
