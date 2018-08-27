package com.kaizensoftware.visitstory.app.dto.user.create;

import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateDTO extends UserDTO {

    @NotNull private String password;

}
