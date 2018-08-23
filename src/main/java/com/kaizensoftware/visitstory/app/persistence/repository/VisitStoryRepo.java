package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface VisitStoryRepo extends SoftDeleteRepository<VisitStory, Long> {

}
