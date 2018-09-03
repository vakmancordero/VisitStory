package com.kaizensoftware.visitstory.app.dto.visit_story.create;

import com.kaizensoftware.visitstory.app.dto.user_permission.create.*;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleVisitStoryCreateDTO extends VisitStoryDTO {

    @NotEmpty
    private Long placeId;

    @NotEmpty
    private Long permissionTypeId;

    private String currentUser;

    private List<UserPermissionCreateDTO> userPermissions;

    private List<MultipartFile> contents;

}
