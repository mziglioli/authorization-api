package com.mz.authorization.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@Setter
public class TokenizeResponse extends DefaultResponse {

    private String email;
    private boolean success;

    @Builder
    public TokenizeResponse(String id, String email, boolean active, boolean success) {
        super(id, active);
        this.email = email;
        this.success = success;
    }

    @JsonIgnore
    public boolean isValid() {
        return isNotEmpty(this.getId()) && isNotEmpty(email);
    }
}
