package com.kaizensoftware.visitstory.app.runner;

import com.kaizensoftware.visitstory.app.config.files.minio.service.MinioTemplate;
import com.kaizensoftware.visitstory.app.service.files.type.VSBucket;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BucketCreatorRunner implements CommandLineRunner {

    private final MinioTemplate minioTemplate;

    @Override
    public void run(String... args) {

        Arrays.stream(VSBucket.values()).forEach(vsBucket -> {

            String bucketName = vsBucket.getBucketName();

            try {
                minioTemplate.createBucket(bucketName);
            } catch (Exception ex) {
                log.error(String.format("Error creating bucket with name %s", bucketName), ex);
            }

        });

    }

}
