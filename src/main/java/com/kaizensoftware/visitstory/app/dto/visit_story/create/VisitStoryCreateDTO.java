package com.kaizensoftware.visitstory.app.dto.visit_story.create;

import com.kaizensoftware.visitstory.app.dto.user_permission.create.*;
import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitStoryCreateDTO extends VisitStoryDTO {

    @NotNull
    private Long placeId;

    @NotNull
    private Long permissionTypeId;

    private List<UserPermissionCreateDTO> userPermissions;

}
