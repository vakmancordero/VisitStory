package com.kaizensoftware.visitstory.app.dto.validation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidPasswordDTO {

    private String password;

}
