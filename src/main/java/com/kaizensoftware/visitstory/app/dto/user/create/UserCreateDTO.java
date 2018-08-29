package com.kaizensoftware.visitstory.app.dto.user.create;

import com.kaizensoftware.visitstory.app.config.validator.password.match.PasswordMatch;
import com.kaizensoftware.visitstory.app.config.validator.password.policy.ValidPassword;
import com.kaizensoftware.visitstory.app.dto.user.UserDTO;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import lombok.Data;

@Data
@PasswordMatch
public class UserCreateDTO extends UserDTO {

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    @ValidPassword
    private String confirmPassword;

    private String confirmationToken;
    private boolean enabled;

    private String phone;
    private String phoneRegion;

    private String birthdaySt;
    private LocalDate birthday;

    @NotNull
    private Long genderReferenceId;

    public boolean checkPasswords() {
        return this.password.equals(confirmPassword);
    }

}
