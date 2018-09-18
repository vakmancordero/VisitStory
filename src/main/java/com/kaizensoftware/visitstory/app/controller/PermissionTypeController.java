package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.service.PermissionTypeService;
import com.kaizensoftware.visitstory.app.dto.permission_type.alter.*;
import com.kaizensoftware.visitstory.app.dto.permission_type.create.*;
import com.kaizensoftware.visitstory.app.dto.permission_type.PermissionTypeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-visit-story/permissionTypes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionTypeController {

    private final PermissionTypeService permissionTypeService;

    @GetMapping
    public ResponseEntity<List<PermissionTypeDTO>> findAll() {
        return ResponseEntity.ok(permissionTypeService.findAllPermissionTypes());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<PermissionTypeDTO>> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(permissionTypeService.findPermissionTypesByName(name));
    }

    @PostMapping
    public ResponseEntity<PermissionTypeCreatedDTO> create(@RequestBody PermissionTypeCreateDTO permissionTypeCreateDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionTypeService.createPermissionType(permissionTypeCreateDTO));
    }

    @PutMapping
    public ResponseEntity<PermissionTypeCreatedDTO> update(@RequestBody PermissionTypeUpdateDTO permissionTypeUpdateDTO) throws Exception {
        return ResponseEntity.ok(permissionTypeService.updatePermissionType(permissionTypeUpdateDTO, false));
    }

    @PatchMapping
    public ResponseEntity<PermissionTypeCreatedDTO> partialUpdate(@RequestBody PermissionTypeUpdateDTO permissionTypeUpdateDTO) throws Exception {
        return ResponseEntity.ok(permissionTypeService.updatePermissionType(permissionTypeUpdateDTO, true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PermissionTypeDeletedDTO> delete(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(permissionTypeService.deletePermissionType(id));
    }

}
