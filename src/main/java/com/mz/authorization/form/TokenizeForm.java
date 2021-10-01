package com.mz.authorization.form;

import com.mz.authorization.annotation.ValidEmail;
import lombok.*;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenizeForm extends DefaultForm {

    @NotEmpty(message = "validator.invalid.email")
    @ValidEmail
    private String email;
}
