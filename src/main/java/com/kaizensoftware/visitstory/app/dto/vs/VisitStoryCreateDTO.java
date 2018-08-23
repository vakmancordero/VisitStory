package com.kaizensoftware.visitstory.app.dto.vs;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VisitStoryCreateDTO {

    private String name;
    private String description;

    @NotNull
    private Long userId;

    @NotNull
    private Long placeId;

    @NotNull
    private Long permissionId;

}
