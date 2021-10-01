package com.mz.authorization.annotation.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecretConstraintValidatorTest {

  private SecretConstraintValidator validator;

  @BeforeEach
  void init() {
    validator = new SecretConstraintValidator();
  }

  @Test
  @DisplayName("Test pass when valid secret")
  void testValid() {
    assertTrue(validator.isValid("1234", null));
  }

  @Test
  @DisplayName("Test pass when empty secret")
  void testValid_WhenEmpty() {
    assertTrue(validator.isValid("", null));
  }

  @Test
  @DisplayName("Test pass when empty null")
  void testValid_WhenNull() {
    assertTrue(validator.isValid(null, null));
  }

  @Test
  @DisplayName("Test pass when empty space secret")
  void testValid_WhenEmptySpace() {
    assertTrue(validator.isValid(" ", null));
  }

  @Test
  @DisplayName("Test NOT pass when NOT valid secret")
  void testNotValid_WhenNotValidEmail() {
    assertFalse(validator.isValid("test.com", null));
  }
}
