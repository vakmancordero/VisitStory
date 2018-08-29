package com.kaizensoftware.visitstory.app.config.validator.password.match;

import javax.validation.Payload;
import javax.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ PARAMETER, TYPE })
@Retention(RUNTIME)
public @interface PasswordMatch {

    String message() default "The passwords does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
