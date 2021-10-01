package com.mz.authorization.controller;

import com.mz.authorization.form.TokenizeForm;
import com.mz.authorization.response.TokenizeResponse;
import com.mz.authorization.service.TokenizeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/password", produces = APPLICATION_JSON_VALUE)
public class PasswordController {

  private final TokenizeService service;
  private static final String LOG_REQUEST = "Request-{}: {}";
  private static final String LOG_REQUEST_ERROR = "Request-Error-{}: {}";

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/reset")
  public Mono<TokenizeResponse> findUserByForm(@Valid @RequestBody TokenizeForm form) {
    log.info(LOG_REQUEST, "reset", form);
    return service.resetPassword(form);
  }
}
