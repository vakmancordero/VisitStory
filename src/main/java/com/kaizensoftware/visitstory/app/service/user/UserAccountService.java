package com.kaizensoftware.visitstory.app.service.user;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.gender_reference.GenderReferenceDTO;
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

import com.kaizensoftware.visitstory.common.util.EventMessage;
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
        if (userExists(email)) throwUserAlreadyExists(email);

        // Verify if the gender reference exists and set it to de dto
        userCreate.setGenderReference(validateGender(userCreate.getGenderReferenceId()));

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
        UserCreatedDTO userCreated = save(userCreate, UserCreatedDTO.class);

        // Send activation message mail
        sendActivationMessage(userCreated);

        return userCreated;
    }

    public EventMessage confirmAccount(String confirmationToken) throws Exception {

        UserConfirmAccountDTO userConfirmAccount = checkConfirmationToken(confirmationToken);
        userConfirmAccount.setEnabled(true);

        update(userConfirmAccount.getId(), userConfirmAccount, UserDTO.class, true);

        return ACCOUNT_CONFIRMATION_MESSAGE;
    }

    private void sendActivationMessage(UserCreatedDTO userCreated) {

        String activationUrl = environment.getProperty(Constants.ACTIVATION_ACCOUNT_URL_PROPERTY);

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
        } catch (Exception ex) {
            throw new ValidationException(INVALID_BIRTHDAY, birthday);
        }

        return birthdayLD;
    }

    private void throwUserAlreadyExists(String email) throws ValidationException {
        throw new ValidationException(USER_ALREADY_EXISTS, email);
    }

    private UserConfirmAccountDTO checkConfirmationToken(String confirmationToken) throws ValidationException {
        return convertUtil.convert(repository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ValidationException(INVALID_CONFIRMATION_TOKEN)), UserConfirmAccountDTO.class);
    }

    private GenderReferenceDTO validateGender(Long genderReferenceId) throws ValidationException {
        try {
            // Convert the found value to the genderReference dto
            return genderReferenceService.findGenderReferenceById(genderReferenceId);
        } catch (Exception ex) {
            throw new ValidationException(INVALID_GENDER_REFERENCE, genderReferenceId);
        }
    }

}
