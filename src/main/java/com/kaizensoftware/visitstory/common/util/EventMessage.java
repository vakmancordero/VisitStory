package com.kaizensoftware.visitstory.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventMessage {

    UNEXPECTED_SERVER_ERROR(1, "Unexpected server error. Call technical support."),
    USER_ALREADY_EXISTS(1, "The user %d already exists.");

    private Integer code;
    private String message;

}
