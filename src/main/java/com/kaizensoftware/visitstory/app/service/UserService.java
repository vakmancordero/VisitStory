package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.auth.User;

import com.kaizensoftware.visitstory.app.service.mail.EmailService;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends BaseService<UserRepo, User> {

    private final PasswordEncoder passwordEncoder;

    private final GenderReferenceService genderReferenceService;
    private final EmailService emailService;

    public UserDTO findUserById(Long id) throws Exception {
        return findById(id, UserDTO.class);
    }

    public UserCreatedDTO createUser(UserCreateDTO userCreate, HttpServletRequest request) throws Exception {

        String email = userCreate.getEmail();

        // Verify that user does not exists
        if (this.userExists(email)) throwUserAlreadyExists(email);

        // Verify if the gender reference exists
        this.validateGender(userCreate.getGenderReferenceId());

        // Parse birthday and set it to the create dto
        userCreate.setBirthday(parseBirthday(userCreate.getBirthdaySt()));

        // Encode the password with passwordEncoder
        userCreate.setPassword(passwordEncoder.encode(userCreate.getPassword()));

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

    private LocalDate parseBirthday(String birthday) throws ValidationException {

        LocalDate birthdayLD;

        try {
            birthdayLD = LocalDate.parse(birthday, DateTimeFormatter.ofPattern(Constants.BIRTHDAY_PATTERN));
        } catch(Exception ex) {
            throw new ValidationException(String.format(INVALID_BIRTHDAY.getMessage(), birthday));
        }

        return birthdayLD;
    }

    private void throwUserAlreadyExists(String email) throws ValidationException {
        throw new ValidationException(String.format(USER_ALREADY_EXISTS.getMessage(), email));
    }

    private void validateGender(Long genderReferenceId) throws ValidationException {
        if (!genderReferenceService.genderReferenceExists(genderReferenceId))
            throw new ValidationException(String.format(USER_GENDER_NOT_EXISTS.getMessage(), genderReferenceId));
    }

}
