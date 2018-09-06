package com.kaizensoftware.visitstory.app.dto.permission_type.create;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionTypeCreatedDTO extends PermissionTypeDTO {

    public PermissionTypeCreatedDTO(String name, String description) {
        super(name, description);
    }
}
