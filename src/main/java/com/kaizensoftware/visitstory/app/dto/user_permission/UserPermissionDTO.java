package com.kaizensoftware.visitstory.app.dto.user_permission;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserPermissionDTO {

    @NotEmpty
    private UserDTO user;

    public UserPermissionDTO(@NotEmpty UserDTO user) {
        this.user = user;
    }
}
