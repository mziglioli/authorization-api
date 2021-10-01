package com.mz.authorization.form;

import com.mz.authorization.annotation.ValidEmail;
import lombok.*;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordForm extends DefaultForm {

    @NotEmpty(message = "validator.invalid.password")
    private String password;

    @NotEmpty(message = "validator.invalid.email")
    @ValidEmail
    private String email;

    @NotEmpty(message = "validator.invalid.token")
    private String token;
}
