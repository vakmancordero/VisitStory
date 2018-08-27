package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import com.kaizensoftware.visitstory.app.dto.permission_type.alter.*;
import com.kaizensoftware.visitstory.app.dto.permission_type.create.*;
import com.kaizensoftware.visitstory.app.persistence.model.PermissionType;

import com.kaizensoftware.visitstory.app.persistence.repository.PermissionTypeRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionTypeService extends BaseService<PermissionTypeRepo, PermissionType> {

    public List<PermissionTypeDTO> findAllPermissionTypes() {
        return findAll(PermissionTypeDTO.class);
    }

    public List<PermissionTypeDTO> findPermissionTypesByName(String name) {

        List<PermissionType> permissionTypes = repository.findByNameIgnoreCaseContaining(name);

        return convertUtils.convert(permissionTypes, PermissionTypeDTO.class);
    }

    public PermissionTypeCreatedDTO createPermissionType(PermissionTypeCreateDTO permissionTypeCreateDTO) throws Exception {
        return create(permissionTypeCreateDTO, PermissionTypeCreatedDTO.class);
    }

    public PermissionTypeCreatedDTO updatePermissionType(PermissionTypeUpdateDTO permissionTypeUpdateDTO, boolean partialUpdate) throws Exception {
        return update(permissionTypeUpdateDTO.getId(), permissionTypeUpdateDTO, PermissionTypeCreatedDTO.class, partialUpdate);
    }

    public PermissionTypeDeletedDTO deletePermissionType(Long permissionTypeId) throws Exception {
        return delete(permissionTypeId, PermissionTypeDeletedDTO.class, false);
    }

}
