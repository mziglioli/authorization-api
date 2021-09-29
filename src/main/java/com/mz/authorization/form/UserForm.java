package com.mz.authorization.form;

import com.mz.authorization.model.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm extends DefaultForm{
    private String name;
    @NotEmpty(message = "validator.invalid.email")
    private String email;
    @NotEmpty(message = "validator.invalid.password")
    private String password;
    private String secret;

    @Override
    public User convertToEntity() {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .id(id)
                .build();
        user.setActive(active);
        return user;
    }
}
