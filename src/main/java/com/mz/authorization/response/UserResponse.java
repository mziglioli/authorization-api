package com.mz.authorization.response;

import lombok.*;

@Getter
@Setter
public class UserResponse extends DefaultResponse {

    private String name;
    private String email;

    @Builder
    public UserResponse(String id, String name, String email, boolean active) {
        super(id, active);
        this.email = email;
        this.name = name;
    }
}
