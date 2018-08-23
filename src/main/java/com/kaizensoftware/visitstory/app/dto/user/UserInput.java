package com.kaizensoftware.visitstory.app.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserInput {

    @NotNull private String email;

    @NotNull private String password;

    @NotNull private String name;
    @NotNull private String lastName;

    private String phone;
    private String about;

}
