package com.kaizensoftware.visitstory.app.service.files;

import com.kaizensoftware.visitstory.app.config.files.minio.dto.MinioObject;
import com.kaizensoftware.visitstory.app.config.files.minio.service.MinioTemplate;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final MinioTemplate minioTemplate;

    @Autowired
    public FileStorageService(MinioTemplate minioTemplate) {
        this.minioTemplate = minioTemplate;
    }

    public MinioObject storeFile(MultipartFile file, String bucketName) {

        try {

            String name = file.getOriginalFilename();

            minioTemplate.saveObject(bucketName, name, file.getInputStream(), file.getSize(), file.getContentType());

            return new MinioObject(minioTemplate.getObjectInfo(bucketName, name), minioTemplate.getEndpoint());

        } catch (Exception ignored) {
            return null;
        }

    }

}
