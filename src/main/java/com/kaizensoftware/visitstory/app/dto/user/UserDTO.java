package com.kaizensoftware.visitstory.app.dto.user;

import com.kaizensoftware.visitstory.app.config.validator.cellphone.Phone;
import com.kaizensoftware.visitstory.app.dto.user.cellphone.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @Email
    @NotEmpty(message = "{user.create.empty.email}")
    private String email;

    @NotEmpty(message = "{user.create.empty.name}")
    private String name;

    @NotEmpty(message = "{user.create.empty.last-name}")
    private String lastName;

    @Phone
    @NotNull(message = "{user.create.invalid.phone-number}")
    private PhoneNumber phoneNumber;

    private String aboutMe;

}
