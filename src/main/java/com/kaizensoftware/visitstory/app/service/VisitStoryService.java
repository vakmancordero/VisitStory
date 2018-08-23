package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.vs.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.app.persistence.repository.VisitStoryRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class VisitStoryService extends BaseService<VisitStoryRepo, VisitStory> {

    public Object makeVisitStory(VisitStoryCreateDTO visitStory) throws Exception {
        return create(visitStory, Object.class);
    }

}
