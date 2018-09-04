package com.kaizensoftware.visitstory.app.service;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.comment.SimpleCommentDTO;
import com.kaizensoftware.visitstory.app.dto.comment.create.CommentCreateDTO;
import com.kaizensoftware.visitstory.app.dto.permission.PermissionDTO;
import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user_permission.UserPermissionDTO;
import com.kaizensoftware.visitstory.app.dto.user_permission.create.*;
import com.kaizensoftware.visitstory.app.dto.visit_story.comment.VisitStoryCommentDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.create.*;

import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.app.persistence.repository.VisitStoryRepo;

import com.kaizensoftware.visitstory.app.service.files.FileStorageService;
import com.kaizensoftware.visitstory.app.service.user.UserService;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryService extends BaseService<VisitStoryRepo, VisitStory> {

    private final UserService userService;
    private final PlaceService placeService;
    private final PermissionTypeService permissionTypeService;
    private final CommentService commentService;

    private final FileStorageService fileStorageService;

    public VisitStoryCreatedDTO makeVisitStory(SimpleVisitStoryCreateDTO simpleVS) throws Exception {

        VisitStoryCreateDTO visitStory = new VisitStoryCreateDTO();

        // Check if the current user exists
        UserDTO user = userService.findUser(simpleVS.getCurrentUser());

        // Check if the place with the given id exists
        PlaceDTO place = placeService.findPlace(simpleVS.getPlaceId());

        // Check if the permission type with the given id exists
        PermissionTypeDTO permissionType = permissionTypeService.findPermission(simpleVS.getPermissionTypeId());

        PermissionDTO permission = new PermissionDTO();
        permission.setPermissionType(permissionType);
        permission.setUserPermissions(userPermissions(simpleVS.getUserPermissions()));

        visitStory.setName(simpleVS.getName());
        visitStory.setDescription(simpleVS.getDescription());
        visitStory.setUser(user);
        visitStory.setPlace(place);
        visitStory.setPermission(permission);
        //visitStoryDTO.setContents(null);

        uploadContent(simpleVS.getContents());

        return create(visitStory, VisitStoryCreatedDTO.class);
    }

    public List<String> findVisitStories(String userName, Pageable pageable) throws Exception {

        UserDTO user = userService.findUser(userName);

        List<VisitStory> visitStories = repository.findByUserId(user.getId(), pageable);



        return null;
    }

    public CommentCreateDTO addComment(SimpleCommentDTO simpleComment) throws Exception {

        CommentCreateDTO commentCreate = new CommentCreateDTO();

        // Check if the current user exists
        UserDTO user = userService.findUser(simpleComment.getCurrentUser());

        // Check if the vs id is valid
        VisitStoryCommentDTO visitStoryComment = checkVSComment(simpleComment.getVisitStoryId());

        commentCreate.setUser(user);
        commentCreate.setVisitStory(visitStoryComment);
        commentCreate.setText(simpleComment.getText());

        return commentService.addCommentVS(commentCreate);
    }

    private VisitStoryCommentDTO checkVSComment(Long visitStoryId) {

        String errorMessage = String.format(INVALID_VISIT_STORY.getMessage(), visitStoryId);

        try {
            return this.findById(visitStoryId, VisitStoryCommentDTO.class);
        } catch (Exception ex) {
            throw new ValidationException(errorMessage);
        }

    }

    private List<UserPermissionDTO> userPermissions(List<UserPermissionCreateDTO> userPermissions) {

        List<Long> userIds = userPermissions.stream().map(UserPermissionCreateDTO::getUserId).collect(Collectors.toList());

        return userService.findAllUsersIn(userIds).stream()
                .map(user -> new UserPermissionDTO(convertUtil.convert(user, UserDTO.class)))
                .collect(Collectors.toList());
    }

    private void uploadContent(List<MultipartFile> contentFiles) {
        contentFiles.forEach(fileStorageService::storeFile);
    }

}
