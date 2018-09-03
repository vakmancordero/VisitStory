package com.kaizensoftware.visitstory.app.service.files;

import com.kaizensoftware.visitstory.app.config.files.FileStorageSettings;
import com.kaizensoftware.visitstory.common.config.exception.FileStorageException;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.io.IOException;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageSettings fileStorageSettings) throws ValidationException {

        this.fileStorageLocation = Paths.get(fileStorageSettings.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new ValidationException("Could not create the directory where the uploaded files will be stored.");
        }

    }

    public String storeFile(MultipartFile file) throws FileStorageException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            //TODO: change strings with event message enum values
            if (fileName.contains(".."))
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

}
