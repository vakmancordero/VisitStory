package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user_permission.create.UserPermissionCreateDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.create.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.app.persistence.repository.VisitStoryRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VisitStoryService extends BaseService<VisitStoryRepo, VisitStory> {

    public Object makeVisitStory(VisitStoryCreateDTO visitStory) throws Exception {

//        List<MultipartFile> contents = visitStory.getContents();
//        List<UserPermissionCreateDTO> userPermissions = visitStory.getUserPermissions();
//        Long permissionTypeId = visitStory.getPermissionTypeId();

        return null;

        //return create(visitStory, Object.class);
    }

}
