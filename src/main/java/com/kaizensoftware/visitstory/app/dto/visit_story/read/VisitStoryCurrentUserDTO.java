package com.kaizensoftware.visitstory.app.dto.visit_story.read;

import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.user.read.UserVSDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;

public class VisitStoryCurrentUserDTO extends VisitStoryDTO {

    private Long id;
    private UserVSDTO user;
    private PlaceDTO place;

}
