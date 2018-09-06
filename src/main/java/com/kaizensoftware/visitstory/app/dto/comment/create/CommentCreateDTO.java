package com.kaizensoftware.visitstory.app.dto.comment.create;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.comment.VisitStoryOnCreateCommentDTO;
import lombok.Data;

@Data
public class CommentCreateDTO {

    private UserDTO user;
    private VisitStoryOnCreateCommentDTO visitStory;
    private String text;

}
