package com.kaizensoftware.visitstory.app.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SimpleCommentDTO {

    @NotNull
    private Long visitStoryId;

    private String currentUser;

    @NotEmpty
    private String text;

}
