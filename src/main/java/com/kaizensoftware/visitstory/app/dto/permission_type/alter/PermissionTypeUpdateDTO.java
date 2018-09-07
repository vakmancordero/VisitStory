package com.kaizensoftware.visitstory.app.dto.permission_type.alter;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionTypeUpdateDTO extends PermissionTypeDTO {

    public PermissionTypeUpdateDTO(String name, String description) {
        setName(name);
        setDescription(description);
    }
}
