package com.kaizensoftware.visitstory.app.service.user;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import com.kaizensoftware.visitstory.app.dto.user.confirm.*;
import com.kaizensoftware.visitstory.common.util.Constants;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;

import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;
import com.kaizensoftware.visitstory.app.persistence.model.auth.User;

import com.kaizensoftware.visitstory.app.service.GenderReferenceService;
import com.kaizensoftware.visitstory.app.service.mail.EmailService;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAccountService extends BaseService<UserRepo, User> {

    private final GenderReferenceService genderReferenceService;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    public UserCreatedDTO createAccount(UserCreateDTO userCreate) throws Exception {

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

        // Set cellphone detail to userCreate dto
        objectUtil.merge(userCreate.getPhoneNumber(), userCreate);

        // Create the user
        UserCreatedDTO userCreated = create(userCreate, UserCreatedDTO.class);

        // Send activation message mail
        this.sendActivationMessage(userCreated);

        return userCreated;
    }

    public String confirmAccount(String confirmationToken) throws Exception {

        UserConfirmAccountDTO userConfirmAccount = checkConfirmationToken(confirmationToken);
        userConfirmAccount.setEnabled(true);

        update(userConfirmAccount.getId(), userConfirmAccount, UserDTO.class, true);

        return ACCOUNT_CONFIRMATION_MESSAGE.getMessage();
    }

    private void sendActivationMessage(UserCreatedDTO userCreated) {

        String activationUrl = environment.getProperty("visit-story.account.register.context");

        String text = String.format(REGISTRATION_CONFIRMATION_CONTENT.getMessage(),
                activationUrl, userCreated.getConfirmationToken());

        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setTo(userCreated.getEmail());
        smm.setSubject(REGISTRATION_CONFIRMATION_SUBJECT.getMessage());
        smm.setText(text);

        emailService.sendMessage(smm);
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

    private UserConfirmAccountDTO checkConfirmationToken(String confirmationToken) throws ValidationException {
        return convertUtil.convert(repository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ValidationException(INVALID_CONFIRMATION_TOKEN.getMessage())), UserConfirmAccountDTO.class);
    }

    private void validateGender(Long genderReferenceId) throws ValidationException {
        if (!genderReferenceService.genderReferenceExists(genderReferenceId))
            throw new ValidationException(String.format(USER_GENDER_NOT_EXISTS.getMessage(), genderReferenceId));
    }

}
