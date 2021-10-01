package com.mz.authorization.form;

import com.mz.authorization.annotation.ValidEmail;
import com.mz.authorization.annotation.ValidSecret;
import com.mz.authorization.model.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm extends DefaultForm {

    @NotEmpty(message = "validator.invalid.name")
    @Size(min = 2, message = "validator.invalid.name")
    private String name;

    @NotEmpty(message = "validator.invalid.initials")
    @Size(min = 2, max = 2, message = "validator.invalid.initials")
    private String initials;

    @NotEmpty(message = "validator.invalid.email")
    @ValidEmail
    private String email;

    @NotEmpty(message = "validator.invalid.password")
    private String password;

    @ValidSecret
    private String secret;

    @Override
    public User convertToEntity() {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .initials(initials)
                .id(id)
                .build();
        user.setActive(active);
        return user;
    }
}
