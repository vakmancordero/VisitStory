package com.kaizensoftware.visitstory.app.service.user;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.dto.user.read.user_contact.UserContactDTO;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.auth.User;

import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends BaseService<UserRepo, User> {

    private final UserAccountService userAccountService;

    public UserDTO findUserById(Long id) throws ValidationException {

        String errorMessage = String.format(NON_EXISTENT_USER.getMessage(), id);

        try {
            return findById(id, UserDTO.class);
        } catch(Exception ex) {
            throw new ValidationException(errorMessage);
        }

    }

    public UserDTO findUser(String email) throws ValidationException {

        String errorMessage = String.format(NON_EXISTENT_USER.getMessage(), email);

        return convertUtil.convert(repository.findByEmail(email).orElseThrow(() ->
                new ValidationException(errorMessage)), UserDTO.class);
    }

    public List<UserContactDTO> findUserContacts(String email) throws ValidationException {

        String errorMessage = String.format(NON_EXISTENT_USER.getMessage(), email);

        User user = repository.findByEmail(email).orElseThrow(() ->
                new ValidationException(errorMessage));

        return user.getUserContacts().stream()
                .map(uc -> convertUtil.convert(uc.getContact(), UserContactDTO.class))
                .collect(Collectors.toList());
    }

    public UserCreatedDTO createAccount(UserCreateDTO userCreate) throws Exception {
        return userAccountService.createAccount(userCreate);
    }

    public void addUserContact(String currentUser, Long userContactId) throws Exception {

        // Check if the current user exists
        UserDTO user = findUser(currentUser);

        // Check if user contact target exists
        UserDTO userContact = findUserById(userContactId);

        
    }

    public String confirmAccount(String confirmationToken) throws Exception {
        return userAccountService.confirmAccount(confirmationToken);
    }

    public List<User> findAllUsersIn(List<Long> userIds) {
        return repository.findAllById(userIds);
    }

}
