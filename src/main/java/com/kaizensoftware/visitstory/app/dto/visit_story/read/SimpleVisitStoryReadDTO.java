package com.kaizensoftware.visitstory.app.dto.visit_story.read;

import com.kaizensoftware.visitstory.app.dto.visit_story.VisitStoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleVisitStoryReadDTO extends VisitStoryDTO {

    private Long id;

}
