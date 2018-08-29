package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.GenderReference;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface GenderReferenceRepo extends SoftDeleteRepository<GenderReference, Long> {

}
