package com.kaizensoftware.visitstory.app.service.files;

import com.kaizensoftware.visitstory.app.dto.content.ContentDTO;
import com.kaizensoftware.visitstory.common.service.BaseService;

import com.kaizensoftware.visitstory.app.persistence.model.Content;
import com.kaizensoftware.visitstory.app.persistence.repository.ContentRepo;

import org.springframework.stereotype.Service;

@Service
public class ContentService extends BaseService<ContentRepo, Content> {

    public ContentDTO uploadContent(ContentDTO content) {

        try {
            return save(content, ContentDTO.class);
        } catch (Exception ex) {
            return null;
        }

    }

}
