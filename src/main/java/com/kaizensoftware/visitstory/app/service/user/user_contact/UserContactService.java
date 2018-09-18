package com.kaizensoftware.visitstory.app.service.user.user_contact;

import com.kaizensoftware.visitstory.app.persistence.model.UserContact;
import com.kaizensoftware.visitstory.app.persistence.repository.UserContactRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserContactService extends BaseService<UserContactRepo, UserContact> {

    public Optional<UserContact> findUserContactRelationship(Long userId, Long contactId) {
        return repository.findByUser_IdAndContact_Id(userId, contactId);
    }

    public UserContact saveUserContact(UserContact userContact) throws Exception {
        return super.save(userContact, UserContact.class);
    }

}
