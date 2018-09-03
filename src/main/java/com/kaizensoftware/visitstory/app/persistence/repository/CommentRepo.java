package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.Comment;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface CommentRepo extends SoftDeleteRepository<Comment, Long> {

}

