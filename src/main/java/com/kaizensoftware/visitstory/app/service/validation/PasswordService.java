package com.kaizensoftware.visitstory.app.service.validation;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordDTO;
import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordOutDTO;
import com.kaizensoftware.visitstory.app.persistence.model.validation.InvalidPassword;
import com.kaizensoftware.visitstory.app.persistence.repository.validation.InvalidPasswordRepo;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PasswordService extends BaseService<InvalidPasswordRepo, InvalidPassword> {

    public InvalidPasswordOutDTO findInvalidPasswordByPassword(String password) throws ValidationException {

        Optional<InvalidPassword> invalidPassword = repository.findByPassword(password);

        return convertUtil.convert(invalidPassword.orElseThrow(() ->
                new ValidationException(NON_EXISTENT_INVALID_PASSWORD, password)), InvalidPasswordOutDTO.class);
    }

    public List<InvalidPasswordOutDTO> findAllInvalidPasswords() {
        return findAll(InvalidPasswordOutDTO.class);
    }

    public InvalidPasswordOutDTO createInvalidPassword(InvalidPasswordDTO invalidPassword) {

        String password = invalidPassword.getPassword();

        try {

            findInvalidPasswordByPassword(password);

        } catch (ValidationException ex) {

            try {
                return save(invalidPassword, InvalidPasswordOutDTO.class);
            } catch (Exception ignored) {
                log.error("Error creating invalid password with value: {}", password);
            }

        }

        return null;
    }

}
