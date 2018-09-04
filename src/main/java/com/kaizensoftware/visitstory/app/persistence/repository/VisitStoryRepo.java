package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.VisitStory;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VisitStoryRepo extends SoftDeleteRepository<VisitStory, Long> {

    List<VisitStory> findByUserId(Long userId, Pageable pageRequest);

}
