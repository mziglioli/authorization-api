package com.mz.authorization.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.authorization.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;

import static com.mz.authorization.config.TestUtils.buildUserResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@EnableCaching
@AutoConfigureWebTestClient
class SecureServiceTest {

  private static final String SESSION = "123456789789";

  @SpyBean
  private JwtService jwtService;

  @Autowired
  private SecureService service;

  @Autowired
  private ObjectMapper mapper;

  @Test
  @DisplayName("Testing create a valid response with token")
  void validateCreateResponseWithToken() {
    given(jwtService.createToken(any())).willReturn("123this.istoken.fortest");
    given(jwtService.getExpires()).willReturn(123L);
    service = new SecureService(jwtService, mapper);

    String token = service.tokenise(buildUserResponse());
    assertEquals("123this.istoken.fortest", token);
  }

  @Test
  @DisplayName("Testing create a valid response with token")
  void validateCreateResponseWithToken_noMocking() {
    service = new SecureService(jwtService, mapper);

    String token = service.tokenise(buildUserResponse());

    // revert it back
    UserResponse user = service.detokenise(token);
    assertEquals("123", user.getId());
    assertEquals("test", user.getName());
    assertEquals("test@test.com", user.getEmail());
  }
}
