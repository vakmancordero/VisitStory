package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.Place;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepo extends SoftDeleteRepository<Place, Long> {

    List<Place> findByNameIgnoreCaseContaining(String name);

    Optional<Place> findByName(String name);

}
