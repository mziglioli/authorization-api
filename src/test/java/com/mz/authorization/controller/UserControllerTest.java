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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mz.authorization.config.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
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
  @DisplayName("given an existing user with email and password exists will return the user auth by admin")
  void test__validUserWhenAdmin() {
    UserForm form = new UserForm("name", "TE", USER_EMAIL, USER_PASSWORD, "1234");
    mock();
    webTestClient
            .post()
            .uri("/user/authenticate")
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .accept(APPLICATION_JSON)
            .bodyValue(form)
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  @DisplayName("given an existing user with email and password exists will return the user auth by user")
  void test__validUserWhenUser() {
    UserForm form = new UserForm("name", "TE", USER_EMAIL, USER_PASSWORD, "1234");
    mock();
    webTestClient
            .post()
            .uri("/user/authenticate")
            .header("Authorization", "Basic d2ViOndlYg==")
            .accept(APPLICATION_JSON)
            .bodyValue(form)
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  @DisplayName("given an user does NOT exists with email and password will return the user")
  void test__invalidUserEmail() {
    UserForm form = new UserForm("name", "TE" ,"usernotexists@email.com", USER_PASSWORD, "");
    mock(form);
    given(repository.findUserByEmailAndActive(form.getEmail(), true))
            .willReturn(Mono.empty());
    webTestClient
            .post()
            .uri("/user/authenticate")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .bodyValue(form)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEmpty()
            .jsonPath("$.name").isEmpty()
            .jsonPath("$.email").isEmpty()
            .jsonPath("$.token").isEmpty();
  }

  @Test
  @DisplayName("given an form is not valid them will return 400 BadRequest")
  void test__invalidUserForm() {
    // empty email
    UserForm form = new UserForm("name", "NM", "", USER_PASSWORD, "");
    webTestClient
            .post()
            .uri("/user/authenticate")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .bodyValue(form)
            .exchange()
            .expectStatus().isBadRequest();

    form = new UserForm("", "", "valid@email.com", "", "");
    webTestClient
            .post()
            .uri("/user/authenticate")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .bodyValue(form)
            .exchange()
            .expectStatus().isBadRequest();
  }

  @Test
  @DisplayName("given a valid form is POST to user will return a 200")
  void test__createUser() {
    UserForm form = new UserForm("new user", "NU", "newuser@email.com", USER_PASSWORD, "");
    mock(form);
    webTestClient
            .post()
            .uri("/user/")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .bodyValue(form)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("new user");
  }

  @Test
  @DisplayName("given an authorized header is present then a response is returned")
  void test__Authorized() {
    mock();
    webTestClient
            .get()
            .uri("/user/all")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
            .exchange()
            .expectStatus()
            .isOk();
  }
  @Test
  @DisplayName("given an user has NOT enough authority to hit that endpoint then an exception is Forbidden with will be returned")
  void test__NotEnoughAuth() {
    mock();
    webTestClient
            .get()
            .uri("/user/all")
            .accept(APPLICATION_JSON)
            .header("Authorization", "Basic d2ViOndlYg==")
            .exchange()
            .expectStatus()
            .isForbidden();
  }

  @Test
  @DisplayName("given an NOT authorized header is present then an error is throw")
  void test__notAuthorized() {
    mock();
    webTestClient
            .get()
            .uri("/user/all")
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();
  }

  private void mock() {
    User user = buildMockUser();
    mock(user);
  }
  private void mock(UserForm form) {
    User user = buildMockUser(form);
    mock(user);
  }
  private void mock(User user) {
    given(repository.findUserByEmailAndActive(user.getEmail(), true))
            .willReturn(Mono.just(user));
    given(repository.findAll())
            .willReturn(Flux.just(user));
    given(repository.save(any()))
            .willReturn(Mono.just(user));
  }
}
