package com.kaizensoftware.visitstory.app.service.user;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.auth.User;

import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends BaseService<UserRepo, User> {

    private final UserAccountService userAccountService;

    public UserDTO findUserById(Long id) throws Exception {
        return findById(id, UserDTO.class);
    }

    public UserDTO findUser(String email) throws ValidationException {

        String errorMessage = String.format(NON_EXISTENT_USER.getMessage(), email);

        return convertUtil.convert(repository.findByEmail(email).orElseThrow(() ->
                new ValidationException(errorMessage)), UserDTO.class);
    }

    public UserCreatedDTO createAccount(UserCreateDTO userCreate) throws Exception {
        return userAccountService.createAccount(userCreate);
    }

    public String confirmAccount(String confirmationToken) throws Exception {
        return userAccountService.confirmAccount(confirmationToken);
    }

}
