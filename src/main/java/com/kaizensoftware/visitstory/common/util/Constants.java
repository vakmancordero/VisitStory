package com.kaizensoftware.visitstory.common.util;

import com.kaizensoftware.visitstory.app.dto.permission_type.create.PermissionTypeCreateDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String BIRTHDAY_PATTERN = "dd/MM/yyyy";
    public static final String ACTIVATION_ACCOUNT_URL_PROPERTY = "visit-story.account.register.context";

    // List of examples of invalid passwords
    public static final List<String> INVALID_PASSWORDS = new ArrayList<>(Arrays.asList(
            "password", "pass123", "some_pass_123", "hello123", "password_123"
    ));

    // List of default gender references
    public static final List<String> GENDER_REFERENCES = new ArrayList<>(Arrays.asList(
            "Masculino", "Femenino"
    ));

    // List of default gender references
    public static final List<PermissionTypeCreateDTO> PERMISSION_TYPES = new ArrayList<>(Arrays.asList(
            new PermissionTypeCreateDTO("Mis contactos", "Sólo los usuarios que tengas agregados podrán ver tus VisitStories"),
            new PermissionTypeCreateDTO("Público", "Todos los usuarios con Visit Story podrán ver tus VisitStories"),
            new PermissionTypeCreateDTO("Restringido", "Puedes elegir quien puede ver y quien no tus VisitStories")
    ));

}
