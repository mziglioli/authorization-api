package com.mz.authorization.service;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import com.mz.authorization.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static com.mz.authorization.config.TestUtils.USER_EMAIL;
import static com.mz.authorization.config.TestUtils.USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

  @SpyBean
  private UserService service;
  @MockBean
  private UserRepository repository;

  @Test
  @DisplayName("given an existing user with email and password exists will return the user")
  void test__validUser() {
    mock();
    UserForm form = new UserForm("name", USER_EMAIL, USER_PASSWORD);
    UserResponse user = service.getByCredentials(form).block();
    assertNotNull(user);
    assertEquals("name", user.getName());
    assertEquals(USER_EMAIL, user.getEmail());
  }

  private void mock() {
    given(repository.findUserByEmailAndPasswordAndActive(USER_EMAIL, USER_PASSWORD, true))
        .willReturn(Mono.just(User.builder()
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .name("test")
                .id("123")
                .build()));
  }
}
