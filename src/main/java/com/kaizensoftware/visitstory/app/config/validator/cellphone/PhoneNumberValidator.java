package com.kaizensoftware.visitstory.app.config.validator.cellphone;

import com.kaizensoftware.visitstory.app.dto.user.cellphone.PhoneNumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<Phone, PhoneNumber> {

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(PhoneNumber phoneNumber, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(phoneNumber.getPhone())
                || StringUtils.isEmpty(phoneNumber.getPhoneRegion()))
            return false;

        try {

            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

            return phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(phoneNumber.getPhone(), phoneNumber.getPhoneRegion()));

        } catch (NumberParseException ex) {
            return false;
        }

    }
}
