package com.mz.authorization.service;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.mz.authorization.config.TestUtils.USER_EMAIL;
import static com.mz.authorization.config.TestUtils.USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

  @SpyBean
  private UserService service;
  @MockBean
  private UserRepository repository;

  @Test
  @DisplayName("given an existing user with email and password exists will return the user")
  void test__validUser() throws Exception {
    mock();
    UserForm form = new UserForm(USER_EMAIL, USER_PASSWORD);
    User user = service.getByCredentials(form).block();
    assertNotNull(user);
    assertEquals(USER_PASSWORD, user.getPassword());
    assertEquals(USER_EMAIL, user.getEmail());
  }

  @Test
  @DisplayName("given an user does NOT exists with email and password will return the user")
  void test__invalidUserEmail() throws Exception {
    mock();
    UserForm form = new UserForm("notexistingemail@test.com", USER_PASSWORD);
    Optional<User> optional = service.getByCredentials(form).blockOptional();
    assertNotNull(optional);
    assertNull(optional.get());
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
