package com.kaizensoftware.visitstory.app.persistence.repository.validation;

import com.kaizensoftware.visitstory.app.persistence.model.validation.InvalidPassword;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.Optional;

public interface InvalidPasswordRepo extends SoftDeleteRepository<InvalidPassword, Long> {

    Optional<InvalidPassword> findByPassword(String password);

}
