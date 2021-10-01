package com.mz.authorization.annotation;

import com.mz.authorization.annotation.validator.SecretConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SecretConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSecret {

  String message() default "validator.invalid.secret";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
