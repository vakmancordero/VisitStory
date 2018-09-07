package com.kaizensoftware.visitstory.app.runner;

import com.kaizensoftware.visitstory.app.service.PermissionTypeService;
import com.kaizensoftware.visitstory.common.util.Constants;

import com.kaizensoftware.visitstory.app.dto.gender_reference.GenderReferenceDTO;
import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordDTO;

import com.kaizensoftware.visitstory.app.service.GenderReferenceService;
import com.kaizensoftware.visitstory.app.service.PlaceService;
import com.kaizensoftware.visitstory.app.service.validation.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseDataRunner implements CommandLineRunner {

    // Required services to create initial values
    private final PasswordService passwordService;
    private final GenderReferenceService genderReferenceService;
    private final PermissionTypeService permissionTypeService;
    private final PlaceService placeService;

    @Override
    public void run(String... args) {

        // Create invalid passwords
        Constants.INVALID_PASSWORDS.stream().map(InvalidPasswordDTO::new)
                .forEach(ip -> log.info("Invalid password created: {}", passwordService.createInvalidPassword(ip)));

        // Create the default gender references
        Constants.GENDER_REFERENCES.stream().map(GenderReferenceDTO::new)
                .forEach(gr -> log.info("GenderReference created: {}", genderReferenceService.createGenderReference(gr)));

        // Create default permission types
        Constants.PERMISSION_TYPES.forEach(pt -> {
            try {
                permissionTypeService.createPermissionType(pt);
            } catch (Exception ex) {
                log.error("Error creating permission type: {}", pt);
            }
        });

        // Create default places
        Constants.PLACES.forEach(pl -> {
            try {
                placeService.createPlace(pl);
            } catch (Exception ex) {
                log.error("Error creating place: {}", pl);
            }
        });

    }

}
