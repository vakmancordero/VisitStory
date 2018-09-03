package com.kaizensoftware.visitstory.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventMessage {

    UNEXPECTED_SERVER_ERROR(1, "Unexpected server error. Call technical support."),
    USER_ALREADY_EXISTS(2, "The user %s already exist."),
    REGISTRATION_CONFIRMATION_SUBJECT(3, "Registration Confirmation."),
    REGISTRATION_CONFIRMATION_CONTENT(4, "To confirm your e-mail address, please click the link below:\n%s/confirm?token=%s"),
    USER_GENDER_NOT_EXISTS(5, "The gender reference with id: %d does not exist."),
    INVALID_BIRTHDAY(6, "Invalid birthday: %s."),
    INVALID_CONFIRMATION_TOKEN(7, "Invalid confirmation token."),
    ACCOUNT_CONFIRMATION_MESSAGE(8, "The user account was successfully confirmed."),
    NON_EXISTENT_USER(9, "The user with email %s does not exist."),
    INVALID_PLACE(10, "The place with id %d does not exist."),
    INVALID_PERMISSION_TYPE(11, "The permission type with id %d does not exist."),
    INVALID_VISIT_STORY(11, "The permission type with id %d does not exist.");

    private Integer code;
    private String message;

}
