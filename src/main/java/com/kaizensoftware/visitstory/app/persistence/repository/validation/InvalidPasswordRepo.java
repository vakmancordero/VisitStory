package com.kaizensoftware.visitstory.app.persistence.repository.validation;

import com.kaizensoftware.visitstory.app.persistence.model.validation.InvalidPassword;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

public interface InvalidPasswordRepo extends SoftDeleteRepository<InvalidPassword, Long> {

}
