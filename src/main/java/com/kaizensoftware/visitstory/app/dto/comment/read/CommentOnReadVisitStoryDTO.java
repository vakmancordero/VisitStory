package com.kaizensoftware.visitstory.app.dto.comment.read;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import lombok.Data;

@Data
public class CommentOnReadVisitStoryDTO {

    private UserDTO user;
    private String text;

}
