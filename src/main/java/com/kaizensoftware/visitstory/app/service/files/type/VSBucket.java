package com.kaizensoftware.visitstory.app.service.files.type;

import lombok.Getter;

@Getter
public enum VSBucket {

    VISIT_STORY_MEDIA       ("visit-story-media"),
    USER_PROFILE_PHOTOS     ("user-profile-photos");

    private String bucketName;

    VSBucket(String bucketName) {
        this.bucketName = bucketName;
    }
}
