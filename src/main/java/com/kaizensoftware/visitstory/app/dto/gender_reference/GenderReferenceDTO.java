package com.kaizensoftware.visitstory.app.dto.gender_reference;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GenderReferenceDTO {

    private Long id;
    private final String description;

}
