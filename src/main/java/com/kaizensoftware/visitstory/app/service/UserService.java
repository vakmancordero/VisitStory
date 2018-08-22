package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.UserInput;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.User;

import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserRepo, User> {

    public List<User> findAllUsers() {
        return super.findAll(User.class);
    }

    public User findUserById(Long id) throws Exception {
        return super.findById(id, User.class);
    }

    public List<User> findAllUsersInactive() {
        return super.findAllInactive(User.class);
    }

    public User findInactiveUserById(Long id) throws Exception {
        return super.findInactiveById(id, User.class);
    }

    public User createUser(UserInput userInput) throws Exception {
        return super.create(userInput, User.class);
    }

    public void deleteAllUsers() throws Exception {
        super.deleteAll();
    }

}
