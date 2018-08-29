package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.cellphone.PhoneNumber;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.User;

import com.kaizensoftware.visitstory.app.service.mail.EmailService;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import com.kaizensoftware.visitstory.common.util.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import static com.kaizensoftware.visitstory.common.util.EventMessage.REGISTRATION_CONFIRMATION_CONTENT;
import static com.kaizensoftware.visitstory.common.util.EventMessage.REGISTRATION_CONFIRMATION_SUBJECT;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends BaseService<UserRepo, User> {

    private final EmailService emailService;

    public UserDTO findUserById(Long id) throws Exception {
        return findById(id, UserDTO.class);
    }

    public UserCreatedDTO createUser(UserCreateDTO userCreate, HttpServletRequest request) throws Exception {

        String email = userCreate.getEmail();

        // Verify that user does not exists
        if (this.userExists(email)) throwUserAlreadyExists(email);

        // The user is not enabled by default
        userCreate.setEnabled(false);
        userCreate.setConfirmationToken(UUID.randomUUID().toString());
        objectUtil.merge(userCreate.getPhoneNumber(), userCreate);

        UserCreatedDTO userCreated = create(userCreate, UserCreatedDTO.class);

        emailService.sendMessage(buildActivationMessage(userCreated, request));

        return userCreated;
    }


    private SimpleMailMessage buildActivationMessage(UserCreatedDTO userCreated, HttpServletRequest request) {

        String url = request.getScheme() + "://" + request.getServerName();
        String text = String.format(REGISTRATION_CONFIRMATION_CONTENT.getMessage(), url, userCreated.getConfirmationToken());

        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setTo(userCreated.getEmail());
        smm.setSubject(REGISTRATION_CONFIRMATION_SUBJECT.getMessage());
        smm.setText(text);

        return smm;
    }

    private boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    private void throwUserAlreadyExists(String email) throws ValidationException {
        throw new ValidationException(String.format(EventMessage.USER_ALREADY_EXISTS.getMessage(), email));
    }

}
