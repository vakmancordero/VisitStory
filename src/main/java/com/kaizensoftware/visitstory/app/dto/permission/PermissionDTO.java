package com.kaizensoftware.visitstory.app.dto.permission;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import com.kaizensoftware.visitstory.app.dto.user_permission.UserPermissionDTO;
import lombok.Data;

import java.util.List;

@Data
public class PermissionDTO {

    private Long id;
    private PermissionTypeDTO permissionType;
    private List<UserPermissionDTO> userPermissions;

}
