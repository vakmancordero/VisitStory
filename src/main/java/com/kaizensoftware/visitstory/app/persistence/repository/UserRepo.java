package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.User;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface UserRepo extends SoftDeleteRepository<User, Long> {

}
