package com.kaizensoftware.visitstory.app.dto.visit_story;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VisitStoryDTO {

    @NotNull
    private String name;

    @NotNull
    private String description;

}
