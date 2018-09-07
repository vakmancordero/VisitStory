package com.kaizensoftware.visitstory.app.dto.validation;

import lombok.Data;

@Data
public class InvalidPasswordDTO {

    private String password;

    public InvalidPasswordDTO(String password) {
        this.password = password;
    }

}
