package com.kaizensoftware.visitstory.app.dto.visit_story.create;

import com.kaizensoftware.visitstory.app.dto.content.ContentDTO;
import com.kaizensoftware.visitstory.app.dto.permission.PermissionDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class VisitStoryCreatedDTO extends VisitStoryDTO {

    private Long id;
    private PlaceDTO place;
    private PermissionDTO permission;
    private List<ContentDTO> contents;

}
