package com.mz.authorization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.authorization.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mz.authorization.config.TestUtils.buildUserResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@EnableCaching
@AutoConfigureWebTestClient
class JwtServiceTest {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private ObjectMapper mapper;

  private final long now = System.currentTimeMillis();

  @Test
  @DisplayName("Testing get correct webRequest user when creating valid token")
  void validate_CreateValidToken() throws Exception {
    String token =
        jwtService.createToken(
            buildJson(), new Date(now), new Date(now + TimeUnit.MINUTES.toMillis(200)));
    String decryptedToken = jwtService.decryptToken(token);
    UserResponse response = mapper.readValue(decryptedToken, UserResponse.class);

    assertEquals("123", response.getId());
    assertEquals("test", response.getName());
    assertEquals("test@test.com", response.getEmail());
  }

  @Test
  @DisplayName(
      "Testing get correct empty webRequest user when token creation date is in the future")
  void validateRequest_When_Invalid_Token_Future() throws Exception {
    String token =
        jwtService.createToken(
            buildJson(), new Date(now - TimeUnit.MINUTES.toMillis(200)), new Date(now));

    Exception exception =
        Assertions.assertThrows(
            Exception.class,
            () -> {
              jwtService.decryptToken(token);
            });
    assertFalse(exception.getMessage().isEmpty());
  }

  @Test
  @DisplayName("Testing get correct empty webRequest user when token is expired")
  void validateRequest_When_Invalid_Token_Expired() throws Exception {
    String token = jwtService.createToken(buildJson(), new Date(now - 1), new Date(now + 1));
    Exception exception =
        Assertions.assertThrows(
            Exception.class,
            () -> {
              jwtService.decryptToken(token);
            });
    assertFalse(exception.getMessage().isEmpty());
  }

  private String buildJson() throws JsonProcessingException {
    return mapper.writeValueAsString(buildUserResponse());
  }
}
