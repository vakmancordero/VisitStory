package com.kaizensoftware.visitstory.app.config.files.minio.dto;

import io.minio.ObjectStat;

import java.util.Date;
import lombok.Data;

@Data
public class MinioObject {

    private String bucketName;
    private String name;
    private Date createdTime;
    private long length;
    private String etag;
    private String contentType;

    public MinioObject(String bucketName, String name, Date createdTime, long length, String etag, String contentType, String matDesc) {
        this.bucketName = bucketName;
        this.name = name;
        this.createdTime = createdTime;
        this.length = length;
        this.etag = etag;
        this.contentType = contentType;
    }

    public MinioObject(ObjectStat os) {
        this.bucketName = os.bucketName();
        this.name = os.name();
        this.createdTime = os.createdTime();
        this.length = os.length();
        this.etag = os.etag();
        this.contentType = os.contentType();
    }

}
