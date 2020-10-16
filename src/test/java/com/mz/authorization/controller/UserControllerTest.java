package com.mz.authorization.controller;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.mz.authorization.config.TestUtils.USER_EMAIL;
import static com.mz.authorization.config.TestUtils.USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@EnableCaching
@AutoConfigureWebTestClient
class UserControllerTest {

  @Autowired
  protected WebTestClient webTestClient;

  @MockBean
  private UserRepository repository;

  @Test
  @DisplayName("given an existing user with email and password exists will return the user")
  void test__validUser() {
    UserForm form = new UserForm(USER_EMAIL, USER_PASSWORD);
    mock();
    webTestClient
            .post()
            .uri("/user/check")
            .accept(APPLICATION_JSON)
            .bodyValue(form)
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  @DisplayName("given an user does NOT exists with email and password will return the user")
  void test__invalidUserEmail() {
    UserForm form = new UserForm("usernotexists@email.com", USER_PASSWORD);
    mock();
    User user = webTestClient
            .post()
            .uri("/user/check")
            .accept(APPLICATION_JSON)
            .bodyValue(form)
            .exchange()
            .expectBody(User.class)
            .returnResult()
            .getResponseBody();
    assertNull(user);
  }

  private void mock() {
    given(repository.findUserByEmailAndPassword(USER_EMAIL, USER_PASSWORD))
        .willReturn(Mono.just(User.builder()
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .name("test")
                .id("123")
                .build()));
  }
}
