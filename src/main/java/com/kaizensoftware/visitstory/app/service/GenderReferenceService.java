package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.gender_reference.GenderReferenceDTO;
import com.kaizensoftware.visitstory.app.persistence.model.GenderReference;
import com.kaizensoftware.visitstory.app.persistence.repository.GenderReferenceRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderReferenceService extends BaseService<GenderReferenceRepo, GenderReference> {

    public List<GenderReferenceDTO> findAllGenderReferences() {
        return findAll(GenderReferenceDTO.class);
    }

    public boolean genderReferenceExists(Long genderReferenceId) {
        return existsById(genderReferenceId);
    }

}
