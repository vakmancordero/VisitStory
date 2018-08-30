package com.kaizensoftware.visitstory.app.config.files;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageSettings {

    private String uploadDir;

}
