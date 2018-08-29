package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.User;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import com.kaizensoftware.visitstory.common.util.EventMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserRepo, User> {

    public UserDTO findUserById(Long id) throws Exception {
        return findById(id, UserDTO.class);
    }

    public UserCreatedDTO createUser(UserCreateDTO userInput) throws Exception {

        String email = userInput.getEmail();

        if (this.userExists(email)) throwUserAlreadyExists(email);

        return create(userInput, UserCreatedDTO.class);
    }

    private boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    private void throwUserAlreadyExists(String email) throws ValidationException {
        throw new ValidationException(String.format(EventMessage.USER_ALREADY_EXISTS.getMessage(), email));
    }

}
