package com.mz.authorization.model;

import com.mz.authorization.response.DefaultResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(of = {"id", "email", "token"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Tokenize extends EntityJpa {
 
    @Id
    private String id;
    private String email;
    private String token;

    @Override
    public DefaultResponse convert() {
        return null;
    }
}