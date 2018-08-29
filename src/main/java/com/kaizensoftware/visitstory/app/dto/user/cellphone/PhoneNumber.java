package com.kaizensoftware.visitstory.app.dto.user.cellphone;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PhoneNumber {

    @NotEmpty
    private String phone;

    @NotEmpty
    private String phoneRegion;

}
