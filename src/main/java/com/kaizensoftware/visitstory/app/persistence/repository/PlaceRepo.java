package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.Place;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.List;

public interface PlaceRepo extends SoftDeleteRepository<Place, Long> {

    List<Place> findByNameIgnoreCaseContaining(String name);

}
