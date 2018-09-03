package com.kaizensoftware.visitstory.app.dto.visit_story.create;

import com.kaizensoftware.visitstory.app.dto.permission.PermissionDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;
import lombok.Data;

@Data
public class VisitStoryCreatedDTO extends VisitStoryDTO {

    private Long id;
    private PermissionDTO permission;
    private PlaceDTO place;

}
