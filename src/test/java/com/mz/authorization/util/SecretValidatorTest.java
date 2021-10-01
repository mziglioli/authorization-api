package com.mz.authorization.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mz.authorization.util.SecretValidator.validate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecretValidatorTest {

  @Test
  @DisplayName("Secret valid")
  void testValid() {
    assertTrue(validate("1234"));
  }

  @Test
  @DisplayName("Secret not valid when null")
  void testNotValid_WhenNull() {
    assertFalse(validate(null));
  }

  @Test
  @DisplayName("Secret not valid when empty")
  void testNotValid_WhenEmpty() {
    assertFalse(validate(""));
  }

  @Test
  @DisplayName("Secret not valid")
  void testNotValid_WhenEmptySpace() {
    assertFalse(validate(" "));
    assertFalse(validate(" abc"));
    assertFalse(validate("abc "));
    assertFalse(validate("abc1"));
    assertFalse(validate("12a3"));
  }

}
