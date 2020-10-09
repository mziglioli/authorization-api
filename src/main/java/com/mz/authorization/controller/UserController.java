package com.mz.authorization.controller;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService service;

  @PostMapping("/check")
  public Mono<User> findUser(@RequestBody UserForm form) {

    log.info("request-find: {}", form);
    return service.getByCredentials(form);
  }

  @GetMapping("/all")
  public Flux<User> findAll() {
    log.info("request-find: {}", "all");
    return service.getAll();
  }
}
