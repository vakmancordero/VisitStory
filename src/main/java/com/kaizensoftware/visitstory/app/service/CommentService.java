package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.comment.create.CommentCreateDTO;
import com.kaizensoftware.visitstory.app.persistence.model.Comment;
import com.kaizensoftware.visitstory.app.persistence.repository.CommentRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService extends BaseService<CommentRepo, Comment> {

    public CommentCreateDTO addCommentVS(CommentCreateDTO commentCreate) throws Exception {
        return create(commentCreate, CommentCreateDTO.class);
    }

}
