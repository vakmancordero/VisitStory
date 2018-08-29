package com.kaizensoftware.visitstory.app.dto.user.create;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreatedDTO extends UserDTO {

    @NotEmpty
    private String confirmationToken;

}
