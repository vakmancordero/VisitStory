package com.kaizensoftware.visitstory.common.config.exception.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends Exception {

    private Object body;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Object body) {
        super(message);
        setBody(body);
    }

}

