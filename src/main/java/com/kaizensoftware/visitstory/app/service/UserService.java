package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.User;

import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserRepo, User> {

    public User findUserById(Long id) throws Exception {
        return findById(id, User.class);
    }

    public User createUser(UserCreateDTO userInput) throws Exception {
        return create(userInput, User.class);
    }

}
