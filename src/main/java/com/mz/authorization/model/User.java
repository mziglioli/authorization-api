package com.mz.authorization.model;

import com.mz.authorization.response.DefaultResponse;
import com.mz.authorization.response.UserResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User extends EntityJpa {
 
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String secret;
    private int loginAttempt;


    @Transient
    public void incrementAttempt() {
        this.loginAttempt = this.loginAttempt + 1;
    }
    @Transient
    public void initLogin() {
        this.loginAttempt = 0;
        this.secret = "";
    }

    @Transient
    @Override
    public DefaultResponse convert() {
        return UserResponse.builder()
                .id(id)
                .email(email)
                .name(name)
                .active(active)
                .loginAttempt(loginAttempt)
                .build();
    }
}