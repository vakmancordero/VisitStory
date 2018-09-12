package com.kaizensoftware.visitstory.app.dto.content;

import com.kaizensoftware.visitstory.app.dto.visit_story.read.SimpleVisitStoryReadDTO;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ContentDTO {

    private String pathReference;
    private String contentType;
    private SimpleVisitStoryReadDTO visitStory;

}
