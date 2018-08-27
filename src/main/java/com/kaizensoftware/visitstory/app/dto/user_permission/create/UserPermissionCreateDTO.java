package com.kaizensoftware.visitstory.app.dto.user_permission.create;

import com.kaizensoftware.visitstory.app.dto.user_permission.UserPermissionDTO;
import lombok.Data;

@Data
public class UserPermissionCreateDTO extends UserPermissionDTO {

    private String email;

}
