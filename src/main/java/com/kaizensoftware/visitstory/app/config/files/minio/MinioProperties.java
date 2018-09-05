package com.kaizensoftware.visitstory.app.config.files.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url, accessKey, secretKey;

}
