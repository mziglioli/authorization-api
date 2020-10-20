package com.mz.authorization.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DefaultResponse {

    private String id;
    private boolean active;
}