package com.kaizensoftware.visitstory.app.dto.permission_type.create;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionTypeCreateDTO extends PermissionTypeDTO {

    public PermissionTypeCreateDTO(String name, String description) {
        setName(name);
        setDescription(description);
    }
}
