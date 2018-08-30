package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user_permission.create.*;
import com.kaizensoftware.visitstory.app.dto.visit_story.create.*;

import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.app.persistence.repository.VisitStoryRepo;

import com.kaizensoftware.visitstory.app.service.files.FileStorageService;
import com.kaizensoftware.visitstory.app.service.user.UserService;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryService extends BaseService<VisitStoryRepo, VisitStory> {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    public Object makeVisitStory(VisitStoryCreateDTO visitStoryDTO) throws Exception {

        UserDTO user = userService.findUser(visitStoryDTO.getCurrentUser());

        visitStoryDTO.getContents().forEach(fileStorageService::storeFile);

        List<UserPermissionCreateDTO> userPermissions = visitStoryDTO.getUserPermissions();

        return null;

        //return create(visitStory, Object.class);
    }

}
