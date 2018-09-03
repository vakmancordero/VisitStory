package com.kaizensoftware.visitstory.app.dto.comment.create;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.comment.VisitStoryCommentDTO;
import lombok.Data;

@Data
public class CommentCreateDTO {

    private UserDTO user;
    private VisitStoryCommentDTO visitStory;
    private String text;

}
