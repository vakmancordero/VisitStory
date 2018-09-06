package com.kaizensoftware.visitstory.app.service.files.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VSBucket {

    VISIT_STORY_MEDIA       ("vsPhotos"),
    USER_PROFILE_PHOTOS     ("vsPhotos");

    private String bucketName;

}
