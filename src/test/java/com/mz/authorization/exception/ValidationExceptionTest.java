package com.mz.authorization.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationExceptionTest {

  @Test
  @DisplayName("Testing constructor")
  void test() {
    ValidationException ex = new ValidationException("test");
    assertNotNull(ex);
    assertEquals("test", ex.getMessage());
  }
}
