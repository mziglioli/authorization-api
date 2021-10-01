package com.mz.authorization.util;

import java.util.regex.Pattern;

public class SecretValidator {

  private SecretValidator() {}

  private static final String SECRET_PATTERN = "^([0-9]{4})$";
  private static final Pattern pattern = Pattern.compile(SECRET_PATTERN);

  public static boolean validate(final String secret) {
    return secret != null && pattern.matcher(secret).matches();
  }
}
