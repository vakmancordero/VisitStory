package com.kaizensoftware.visitstory.app.dto.visit_story;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VisitStoryDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

}
