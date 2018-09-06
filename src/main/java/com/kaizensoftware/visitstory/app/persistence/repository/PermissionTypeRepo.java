package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.PermissionType;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionTypeRepo extends SoftDeleteRepository<PermissionType, Long> {

    List<PermissionType> findByNameIgnoreCaseContaining(String name);

    Optional<PermissionType> findByName(String name);

}
