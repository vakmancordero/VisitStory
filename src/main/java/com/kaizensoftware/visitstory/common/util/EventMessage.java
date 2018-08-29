package com.kaizensoftware.visitstory.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventMessage {

    UNEXPECTED_SERVER_ERROR(1, "Unexpected server error. Call technical support."),
    USER_ALREADY_EXISTS(2, "The user %s already exists."),
    REGISTRATION_CONFIRMATION_SUBJECT(3, "Registration Confirmation"),
    REGISTRATION_CONFIRMATION_CONTENT(4, "To confirm your e-mail address, please click the link below:\n%s/confirm?token=%s");

    private Integer code;
    private String message;

}
