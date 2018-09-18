package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.Content;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface ContentRepo extends SoftDeleteRepository<Content, Long> {

}

