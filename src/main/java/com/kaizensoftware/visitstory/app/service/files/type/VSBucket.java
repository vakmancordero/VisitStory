package com.kaizensoftware.visitstory.app.service.files.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VSBucket {

    VISIT_STORY_MEDIA       ("visit-story-media"),
    USER_PROFILE_PHOTOS     ("user-profile-photos");

    private String bucketName;

}
