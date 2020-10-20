package com.mz.authorization.config;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    public final static String USER_EMAIL = "test@test.com";
    public final static String USER_PASSWORD = "123";

    public static User buildMockUser() {
        return User.builder()
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .name("test")
                .id("123")
                .build();
    }
    public static User buildMockUser(UserForm form) {
        return User.builder()
                .password(form.getPassword())
                .email(form.getEmail())
                .name(form.getName())
                .id(form.getId())
                .build();
    }
}
