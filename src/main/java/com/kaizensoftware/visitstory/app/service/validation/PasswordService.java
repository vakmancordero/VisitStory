package com.kaizensoftware.visitstory.app.service.validation;

import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordDTO;
import com.kaizensoftware.visitstory.app.persistence.model.validation.InvalidPassword;
import com.kaizensoftware.visitstory.app.persistence.repository.validation.InvalidPasswordRepo;
import com.kaizensoftware.visitstory.common.service.BaseService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService extends BaseService<InvalidPasswordRepo, InvalidPassword> {

    public List<InvalidPasswordDTO> findAllInvalidPasswords() {
        return findAll(InvalidPasswordDTO.class);
    }

}
