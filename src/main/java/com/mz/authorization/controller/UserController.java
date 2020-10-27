package com.mz.authorization.controller;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.response.JwtResponse;
import com.mz.authorization.response.UserResponse;
import com.mz.authorization.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService service;
  private static final String LOG_REQUEST = "Request-{}: {}";
  private static final String LOG_REQUEST_ERROR = "Request-Error-{}: {}";

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/authenticate")
  public Mono<JwtResponse> findUserByForm(@RequestBody UserForm form) {
    log.info(LOG_REQUEST, "find", form);
    return service.authenticate(form);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/check/{token}")
  public Mono<UserResponse> checkUserByToken(@PathVariable String token) {
    log.info(LOG_REQUEST, "check", token);
    return service.check(token);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> add(@RequestBody UserForm form) {
    log.info(LOG_REQUEST, "add", form);
    return service.add(form);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> update(@PathVariable String id, @RequestBody UserForm form) {
    log.info(LOG_REQUEST, "update", id);
    if(isBlank(id) || isBlank(form.getId()) || !id.equals(form.getId())) {
      // TODO
      throw new RuntimeException("Invalid id to update");
    }
    return service.update(form);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> disable(@PathVariable String id) {
    log.info(LOG_REQUEST, "delete", id);
    if(isBlank(id)) {
      // TODO
      throw new RuntimeException("Invalid id to disable");
    }
    return service.delete(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public Mono<UserResponse> findById(@PathVariable String id) {
    log.info(LOG_REQUEST, "find:", id);
    return service.getById(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public Flux<UserResponse> findAll() {
    log.info(LOG_REQUEST, "find", "all");
    return service.getAll();
  }
}
