package com.mz.authorization.annotation.validator;


import com.mz.authorization.annotation.ValidSecret;
import com.mz.authorization.util.SecretValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class SecretConstraintValidator implements ConstraintValidator<ValidSecret, String> {

  @Override
  public boolean isValid(String secret, ConstraintValidatorContext context) {
    return isBlank(secret) || SecretValidator.validate(secret);
  }
}
