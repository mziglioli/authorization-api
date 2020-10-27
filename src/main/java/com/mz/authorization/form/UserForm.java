package com.mz.authorization.form;

import com.mz.authorization.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class UserForm extends DefaultForm{
    @NotEmpty(message = "validator.invalid.name")
    private String name;
    @NotEmpty(message = "validator.invalid.email")
    private String email;
    @NotEmpty(message = "validator.invalid.password")
    private String password;

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
