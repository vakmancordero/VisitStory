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
    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastName;

    @Phone
    @NotNull
    private PhoneNumber phoneNumber;

    private String aboutMe;

}
