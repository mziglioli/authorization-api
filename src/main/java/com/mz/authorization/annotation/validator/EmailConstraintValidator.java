package com.mz.authorization.annotation.validator;


import com.mz.authorization.annotation.ValidEmail;
import com.mz.authorization.util.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return isBlank(email) || EmailValidator.validate(email);
  }
}
