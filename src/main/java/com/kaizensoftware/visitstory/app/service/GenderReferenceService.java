package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.gender_reference.GenderReferenceDTO;
import com.kaizensoftware.visitstory.app.persistence.model.GenderReference;
import com.kaizensoftware.visitstory.app.persistence.repository.GenderReferenceRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GenderReferenceService extends BaseService<GenderReferenceRepo, GenderReference> {

    public List<GenderReferenceDTO> findAllGenderReferences() {
        return findAll(GenderReferenceDTO.class);
    }

    public boolean genderReferenceExists(Long genderReferenceId) {
        return existsById(genderReferenceId);
    }

    public boolean genderReferenceExists(String description) {
        return repository.findByDescription(description).isPresent();
    }

    public GenderReferenceDTO createGenderReference(GenderReferenceDTO genderReference) {

        if (!this.genderReferenceExists(genderReference.getDescription())) {

            try {

                return create(genderReference, GenderReferenceDTO.class);

            } catch (Exception ignored) {

                log.error("Error creating genderReference: {}", genderReference);

                return null;
            }

        }

        return genderReference;
    }

}
