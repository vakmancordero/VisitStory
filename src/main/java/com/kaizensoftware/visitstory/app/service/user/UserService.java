package com.kaizensoftware.visitstory.app.service.user;

import static com.kaizensoftware.visitstory.common.util.Constants.*;
import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.google.common.collect.ImmutableMap;
import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.dto.user.read.user_contact.UserContactDTO;
import com.kaizensoftware.visitstory.app.persistence.model.UserContact;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.auth.User;

import com.kaizensoftware.visitstory.app.service.user.user_contact.UserContactService;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import com.kaizensoftware.visitstory.common.util.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends BaseService<UserRepo, User> {

    // User service use multiple services that wrap all methods about users.
    private final UserAccountService userAccountService;
    private final UserContactService userContactService;

    public UserDTO findUserById(Long id) throws ValidationException {
        try {
            return findById(id, UserDTO.class);
        } catch (Exception ex) {
            throw new ValidationException(INVALID_USER, id);
        }
    }

    public UserDTO findUser(String email) throws ValidationException {
        return convertUtil.convert(repository.findByEmail(email).orElseThrow(() ->
                new ValidationException(NON_EXISTENT_USER, email)), UserDTO.class);
    }

    public List<UserContactDTO> findUserContacts(String email) throws ValidationException {

        // Find user by email, throw an exception if not exist
        User user = repository.findByEmail(email).orElseThrow(() ->
                new ValidationException(NON_EXISTENT_USER, email));

        // Map all the user contacts to user contact dto
        return user.getUserContacts().stream()
                .map(uc -> convertUtil.convert(uc.getContact(), UserContactDTO.class))
                .collect(Collectors.toList());
    }

    public UserCreatedDTO createAccount(UserCreateDTO userCreate) throws Exception {

        // Call a method of user account service
        return userAccountService.createAccount(userCreate);
    }

    public EventMessage addUserContact(String currentUser, Long userContactId) throws Exception {

        // Check if the given users exist
        ImmutableMap<String, User> relationship = validateUserContactRelationship(currentUser, userContactId);

        User user = relationship.get(CURRENT_USER);
        User toUserContact = relationship.get(TO_USER_CONTACT);

        // Validate if the user can add the target contact
        this.canAddContact(user, toUserContact);

        // Add the user contact
        user.getUserContacts().add(new UserContact(user, toUserContact));

        // Ignored
        super.save(user, UserDTO.class);

        return USER_CONTACT_REQUEST_SUCCESS;
    }

    public EventMessage removeUserContact(String currentUser, Long userContactId) throws Exception {

        // Check if the given users exist
        ImmutableMap<String, User> relationship = validateUserContactRelationship(currentUser, userContactId);

        User user = relationship.get(CURRENT_USER);
        User toUserContact = relationship.get(TO_USER_CONTACT);

        UserContact userContact = findUserContact(user.getId(), toUserContact.getId()).orElseThrow(() ->
                new ValidationException(ADD_USER_CONTACT_NOT_IN_LIST));

        user.getUserContacts().remove(userContact);

        // Ignored
        super.save(user, UserDTO.class);

        return USER_CONTACT_REMOVED_SUCCESSFULLY;
    }

    public EventMessage blockUserContact(String currentUser, Long userContactId) throws Exception {

        // Check if the given users exist
        ImmutableMap<String, User> relationship = validateUserContactRelationship(currentUser, userContactId);

        User user = relationship.get(CURRENT_USER);
        User toUserContact = relationship.get(TO_USER_CONTACT);

        UserContact userContact = findUserContact(user.getId(), toUserContact.getId()).orElseThrow(() ->
                new ValidationException(ADD_USER_CONTACT_NOT_IN_LIST));

        userContact.setBlocked(true);

        // Ignored
        userContact = saveUserContact(userContact);

        return USER_CONTACT_REMOVED_SUCCESSFULLY;
    }

    private ImmutableMap<String, User> validateUserContactRelationship(String currentUser, Long userContactId) throws ValidationException {

        // Check if the current user exists
        User user = repository.findByEmail(currentUser).orElseThrow(() ->
                new ValidationException(NON_EXISTENT_USER, currentUser));

        // Check if user contact target exists
        User toUserContact = repository.findById(userContactId).orElseThrow(() ->
                new ValidationException(INVALID_USER, userContactId));

        return ImmutableMap.<String, User>builder()
                .put(CURRENT_USER, user) // Add the current user to map
                .put(TO_USER_CONTACT, toUserContact) // Add the user contact to map
                .build();
    }

    private void canAddContact(User user, User toUserContact) throws ValidationException {

        // The user cannot be himself
        if (user.getId().equals(toUserContact.getId()))
            throw new ValidationException(ADD_USER_CONTACT_SAME_USER_ERROR);

        // Find in user contact list if the toUserContact exists, if exists, throw and validation exception
        if (findUserContact(user.getId(), toUserContact.getId()).isPresent())
            throw new ValidationException(ADD_USER_CONTACT_ALREADY_IN_LIST);

    }

    private Optional<UserContact> findUserContact(Long userId, Long toUserContactId) {
        return userContactService.findUserContactRelationship(userId, toUserContactId);
    }

    private UserContact saveUserContact(UserContact userContact) throws Exception {
        return userContactService.saveUserContact(userContact);
    }

    public EventMessage confirmAccount(String confirmationToken) throws Exception {
        return userAccountService.confirmAccount(confirmationToken);
    }

    public List<User> findAllUsersIn(List<Long> userIds) {
        return repository.findAllById(userIds);
    }

}
