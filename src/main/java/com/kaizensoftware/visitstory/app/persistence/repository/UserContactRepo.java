package com.kaizensoftware.visitstory.app.persistence.repository;

import com.kaizensoftware.visitstory.app.persistence.model.UserContact;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;

import java.util.Optional;

public interface UserContactRepo extends SoftDeleteRepository<UserContact, Long> {

    Optional<UserContact> findByUser_IdAndContact_Id(Long userId, Long contactId);

}
