package com.kaizensoftware.visitstory.app.dto.user_permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserPermissionDTO {

    @NotNull
    private Long userId;

}
