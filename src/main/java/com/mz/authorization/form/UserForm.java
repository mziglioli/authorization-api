package com.mz.authorization.form;

import com.mz.authorization.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserForm extends DefaultForm{
    private String name;
    private String email;
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
