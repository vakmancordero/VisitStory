package com.kaizensoftware.visitstory.app.service;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;
import com.kaizensoftware.visitstory.app.dto.permission_type.alter.*;
import com.kaizensoftware.visitstory.app.dto.permission_type.create.*;
import com.kaizensoftware.visitstory.app.persistence.model.PermissionType;

import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;

import com.kaizensoftware.visitstory.app.persistence.repository.PermissionTypeRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionTypeService extends BaseService<PermissionTypeRepo, PermissionType> {

    public PermissionTypeDTO findByPermissionTypeId(Long permissionTypeId) throws Exception {
        return findById(permissionTypeId, PermissionTypeDTO.class);
    }

    public PermissionTypeDTO findByPermissionTypeByName(String name) throws ValidationException {

        String errorMessage = String.format(NON_EXISTENT_PERMISSION_TYPE.getMessage(), name);

        Optional<PermissionType> permissionType = repository.findByName(name);

        return convertUtil.convert(permissionType.orElseThrow(() -> new ValidationException(errorMessage)), PermissionTypeDTO.class);
    }

    public List<PermissionTypeDTO> findAllPermissionTypes() {
        return findAll(PermissionTypeDTO.class);
    }

    public List<PermissionTypeDTO> findPermissionTypesByName(String name) {

        List<PermissionType> permissionTypes = repository.findByNameIgnoreCaseContaining(name);

        return convertUtil.convert(permissionTypes, PermissionTypeDTO.class);
    }

    public PermissionTypeCreatedDTO createPermissionType(PermissionTypeCreateDTO permissionTypeCreateDTO) throws Exception {

        try {

            PermissionTypeDTO permissionType = findByPermissionTypeByName(permissionTypeCreateDTO.getName());

            return convertUtil.convert(permissionType, PermissionTypeCreatedDTO.class);

        } catch(ValidationException ex) {
            return create(permissionTypeCreateDTO, PermissionTypeCreatedDTO.class);
        }

    }

    public PermissionTypeCreatedDTO updatePermissionType(PermissionTypeUpdateDTO permissionTypeUpdateDTO, boolean partialUpdate) throws Exception {
        return update(permissionTypeUpdateDTO.getId(), permissionTypeUpdateDTO, PermissionTypeCreatedDTO.class, partialUpdate);
    }

    public PermissionTypeDeletedDTO deletePermissionType(Long permissionTypeId) throws Exception {
        return delete(permissionTypeId, PermissionTypeDeletedDTO.class, false);
    }

    public PermissionTypeDTO findPermission(Long permissionId) throws Exception {

        String errorMessage = String.format(INVALID_PERMISSION_TYPE.getMessage(), permissionId);

        try {
            return this.findByPermissionTypeId(permissionId);
        } catch(EntityNotFoundException ex) {
            throw new ValidationException(errorMessage);
        }

    }

}
