package com.kaizensoftware.visitstory.app.service;

// Exceptions
import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

// Comment DTOs
import com.kaizensoftware.visitstory.app.config.files.minio.dto.MinioObject;
import com.kaizensoftware.visitstory.app.dto.comment.SimpleCommentDTO;
import com.kaizensoftware.visitstory.app.dto.comment.create.CommentCreateDTO;

// Permission DTOs
import com.kaizensoftware.visitstory.app.dto.content.ContentDTO;
import com.kaizensoftware.visitstory.app.dto.permission.PermissionDTO;
import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;

// Place DTOs
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;

// UserPermission DTOs
import com.kaizensoftware.visitstory.app.dto.user_permission.UserPermissionDTO;
import com.kaizensoftware.visitstory.app.dto.user_permission.create.*;

// VisitStory DTOs
import com.kaizensoftware.visitstory.app.dto.visit_story.comment.VisitStoryOnCreateCommentDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.create.*;

// Required persistence classes for this service
import com.kaizensoftware.visitstory.app.dto.visit_story.read.SimpleVisitStoryReadDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.read.VisitStoryCurrentUserDTO;
import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.app.persistence.repository.VisitStoryRepo;
import com.kaizensoftware.visitstory.app.service.files.ContentService;
import com.kaizensoftware.visitstory.app.service.files.type.VSBucket;
import com.kaizensoftware.visitstory.common.service.BaseService;

// Required services
import com.kaizensoftware.visitstory.app.service.files.FileStorageService;
import com.kaizensoftware.visitstory.app.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryService extends BaseService<VisitStoryRepo, VisitStory> {

    private final UserService userService;
    private final PlaceService placeService;
    private final PermissionTypeService permissionTypeService;
    private final CommentService commentService;
    private final ContentService contentService;

    private final FileStorageService fileStorageService;

    public VisitStoryCreatedDTO makeVisitStory(SimpleVisitStoryCreateDTO simpleVS) throws Exception {

        // Create a new instance of visitStory
        VisitStoryCreateDTO visitStory = new VisitStoryCreateDTO();

        // Check if the current user exists
        UserDTO user = userService.findUser(simpleVS.getCurrentUser());

        // Check if the place with the given id exists
        PlaceDTO place = placeService.findPlace(simpleVS.getPlaceId());

        // Check if the permission type with the given id exists
        PermissionTypeDTO permissionType = permissionTypeService.findPermission(simpleVS.getPermissionTypeId());

        // Create a new permission for the visitStory
        PermissionDTO permission = new PermissionDTO();
        permission.setPermissionType(permissionType);
        permission.setUserPermissions(userPermissions(simpleVS.getUserPermissions()));

        // Setting all the attributes for the visitStory creation
        visitStory.setName(simpleVS.getName());
        visitStory.setDescription(simpleVS.getDescription());
        visitStory.setUser(user);
        visitStory.setPlace(place);
        visitStory.setPermission(permission);

        // Create the visitStory
        VisitStoryCreatedDTO visitStoryCreated = save(visitStory, VisitStoryCreatedDTO.class);

        // Upload the content files and return its reference
        List<ContentDTO> content = uploadContent(simpleVS.getContents(), // The contentFiles
                VSBucket.VISIT_STORY_MEDIA.getBucketName(), // The bucketName for this operation
                visitStoryCreated // The visitStory id created
        );

        visitStoryCreated.setContents(content);

        content.forEach(contentService::uploadContent);

        return visitStoryCreated;
    }

    public List<VisitStoryCurrentUserDTO> findVisitStories(String userName, Pageable pageable) throws Exception {

        // Verify if the user exists and return it
        UserDTO user = userService.findUser(userName);

        // Find all the visitStories of the user by pageable param and iterate it
        //List<VisitStory> visitStories = repository.findByUserId(user.getId(), pageable);
        List<VisitStory> visitStories = repository.findByUserId(user.getId());

        // Find all the visitStories of the user by pageable param and iterate it
        return visitStories.stream().map(vs -> {
        //return repository.findByUserId(user.getId(), pageable).stream().map(vs -> {

            // Create new dto instance to copy the vs entity values to it
            VisitStoryCurrentUserDTO visitStory = new VisitStoryCurrentUserDTO();

            // Make the mapping
            convertUtil.map(vs, visitStory);

            // Return it to the stream
            return visitStory;

            // And we collect to the list

        }).collect(Collectors.toList());
    }

    public CommentCreateDTO addComment(SimpleCommentDTO simpleComment) throws Exception {

        // Create an instance of Create-Comment
        CommentCreateDTO commentCreate = new CommentCreateDTO();

        // Check if the current user exists
        UserDTO user = userService.findUser(simpleComment.getCurrentUser());

        // Check if the vs id is valid
        VisitStoryOnCreateCommentDTO visitStoryComment = checkVSComment(simpleComment.getVisitStoryId());

        commentCreate.setUser(user);
        commentCreate.setVisitStory(visitStoryComment);
        commentCreate.setText(simpleComment.getText());

        return commentService.addCommentVS(commentCreate);
    }

    private VisitStoryOnCreateCommentDTO checkVSComment(Long visitStoryId) {

        // Error message for non existing vs
        String errorMessage = String.format(INVALID_VISIT_STORY.getMessage(), visitStoryId);

        try {

            // Convert the found value to the onCreateComment dto
            return this.findById(visitStoryId, VisitStoryOnCreateCommentDTO.class);
        } catch (Exception ex) {
            throw new ValidationException(errorMessage);
        }

    }

    private List<UserPermissionDTO> userPermissions(List<UserPermissionCreateDTO> userPermissions) {

        // Convert the list to only IDs
        List<Long> userIds = userPermissions.stream().map(UserPermissionCreateDTO::getUserId).collect(Collectors.toList());

        // Collect all the users and save a new UserPermission dto
        return userService.findAllUsersIn(userIds).stream()
                .map(user -> new UserPermissionDTO(convertUtil.convert(user, UserDTO.class)))
                .collect(Collectors.toList());
    }

    private List<ContentDTO> uploadContent(List<MultipartFile> contentFiles, String bucketName, VisitStoryCreatedDTO visitStoryCreated) {

        // Iterate all the contentFiles of vs
        return contentFiles.stream().map(file -> {

            // Save the multiPartFile into mini-o
            MinioObject mo = fileStorageService.storeFile(file, bucketName);

            SimpleVisitStoryReadDTO simpleVisitStoryRead = convertUtil.convert(visitStoryCreated, SimpleVisitStoryReadDTO.class);

            // Only create a content dto if the value is not null
            return mo != null ? new ContentDTO(mo.getPath(), mo.getContentType(), simpleVisitStoryRead) : null;

            // Return only non null values
        }).filter(Objects::nonNull).collect(Collectors.toList());

    }

}
