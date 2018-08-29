package com.kaizensoftware.visitstory.app.config.validator.password.match;

import com.kaizensoftware.visitstory.app.dto.user.create.UserCreateDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserCreateDTO> {

    private static final String NOT_MATCH = "The passwords does not match";

    @Override
    public boolean isValid(UserCreateDTO user, ConstraintValidatorContext context) {

        boolean match = user.checkPasswords();

        if (!match) {
            context.buildConstraintViolationWithTemplate(NOT_MATCH)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return match;
    }
}
