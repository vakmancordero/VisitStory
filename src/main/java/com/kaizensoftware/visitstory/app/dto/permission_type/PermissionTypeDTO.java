package com.kaizensoftware.visitstory.app.dto.permission_type;

import lombok.Data;

@Data
public class PermissionTypeDTO {

    private Long id;
    private final String name;
    private final String description;

    public PermissionTypeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
