package com.kaizensoftware.visitstory.app.dto.user.create;

import com.kaizensoftware.visitstory.app.config.validator.password.match.PasswordMatch;
import com.kaizensoftware.visitstory.app.config.validator.password.policy.ValidPassword;
import com.kaizensoftware.visitstory.app.dto.user.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
@PasswordMatch
public class UserCreateDTO extends UserDTO {

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    @ValidPassword
    private String confirmPassword;

    public boolean checkPasswords() {
        return this.password.equals(confirmPassword);
    }

}
