package com.kaizensoftware.visitstory.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventMessage {

    UNEXPECTED_SERVER_ERROR(1, "Unexpected server error. Call technical support."),
    USER_ALREADY_EXISTS(2, "The user %s already exist."),
    REGISTRATION_CONFIRMATION_SUBJECT(3, "Registration Confirmation."),
    REGISTRATION_CONFIRMATION_CONTENT(4, "To confirm your e-mail address, please click the link below:\n%s/confirm/%s"),
    USER_GENDER_NOT_EXISTS(5, "The gender reference with id: %d does not exist."),
    INVALID_BIRTHDAY(6, "Invalid birthday: %s."),
    INVALID_CONFIRMATION_TOKEN(7, "Invalid confirmation token."),
    ACCOUNT_CONFIRMATION_MESSAGE(8, "The user account was successfully confirmed."),
    NON_EXISTENT_USER(9, "The user with email %s does not exist."),
    INVALID_USER(10, "The user with id %d does not exist."),
    INVALID_PLACE(11, "The user with id %d does not exist."),
    INVALID_PERMISSION_TYPE(12, "The permission type with id %d does not exist."),
    NON_EXISTENT_PERMISSION_TYPE(13, "The permission type with name %s does not exist."),
    INVALID_VISIT_STORY(14, "The visit story with id %d does not exist."),
    NON_EXISTENT_INVALID_PASSWORD(15, "The invalid password with value %s does not exist."),
    NON_EXISTENT_PLACE(16, "The place with name %s does not exist."),
    INVALID_GENDER_REFERENCE(17, "The gender reference with id %d does not exist."),
    USER_CONTACT_REQUEST_SUCCESS(18, "The user contact invitation was successfully sent."),
    USER_CONTACT_REMOVED_SUCCESSFULLY(19, "The user contact was removed successfully."),
    USER_CONTACT_BLOCKED_SUCCESSFULLY(20, "The user contact was blocked successfully."),
    ADD_USER_CONTACT_SAME_USER_ERROR(21, "The user contact cannot be the same as the current."),
    ADD_USER_CONTACT_ALREADY_IN_LIST(22, "The user contact already exists in your user contact list."),
    ADD_USER_CONTACT_NOT_IN_LIST(23, "The user contact does not exists in your user contact list.");

    private Integer code;
    private String message;

}
