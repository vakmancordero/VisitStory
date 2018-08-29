package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.auth.User;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.Optional;

public interface UserRepo extends SoftDeleteRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
