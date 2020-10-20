package com.mz.authorization.controller;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.response.UserResponse;
import com.mz.authorization.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/check")
  public Mono<UserResponse> findUserByForm(@RequestBody UserForm form) {

    log.info("request-find: {}", form);
    return service.getByCredentials(form);
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> add(@RequestBody UserForm form) {
    log.info("request-add: {}", form);
    return service.add(form);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> update(@PathVariable String id, @RequestBody UserForm form) {
    log.info("request-update: {}", id);
    if(isBlank(id) || isBlank(form.getId()) || !id.equals(form.getId())) {
      // TODO
      throw new RuntimeException("Invalid id to update");
    }
    return service.update(form);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserResponse> disable(@PathVariable String id) {
    log.info("request-delete: {}", id);
    if(isBlank(id)) {
      // TODO
      throw new RuntimeException("Invalid id to disable");
    }
    return service.delete(id);
  }

  @GetMapping("/{id}")
  public Mono<UserResponse> findById(@PathVariable String id) {
    log.info("request-find: {}", id);
    return service.getById(id);
  }

  @GetMapping("/all")
  public Flux<UserResponse> findAll() {
    log.info("request-find: {}", "all");
    return service.getAll();
  }
}
