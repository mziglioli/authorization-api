package com.mz.authorization.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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

    @JsonIgnore
    public boolean isValid() {
        return isNotEmpty(this.getId()) && isNotEmpty(name) && isNotEmpty(email);
    }
}
