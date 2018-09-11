package com.kaizensoftware.visitstory.app.dto.visit_story.read;

import com.kaizensoftware.visitstory.app.dto.comment.read.CommentOnReadVisitStoryDTO;
import com.kaizensoftware.visitstory.app.dto.content.ContentDTO;
import com.kaizensoftware.visitstory.app.dto.permission.PermissionDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.user.read.visit_story.UserVSDTO;
import com.kaizensoftware.visitstory.app.dto.view_.ViewVSDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitStoryCurrentUserDTO extends VisitStoryDTO {

    private Long id;
    private UserVSDTO user;
    private PlaceDTO place;
    private PermissionDTO permission;
    private List<ViewVSDTO> views;
    private List<ContentDTO> contents;
    private List<CommentOnReadVisitStoryDTO> comments;

}
