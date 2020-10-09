package com.mz.authorization.service;

import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserService {

    private final UserRepository repository;

    public Mono<User> getByCredentials(UserForm form) {
        return repository.findUserByEmailAndPassword(form.getEmail(), form.getPassword());
    }

    public Flux<User> getAll() {
        return repository.findAll();
    }
}
