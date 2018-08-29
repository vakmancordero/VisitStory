package com.kaizensoftware.visitstory.app.dto.user_permission;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserPermissionDTO {

    @NotEmpty
    private Long userId;

}
