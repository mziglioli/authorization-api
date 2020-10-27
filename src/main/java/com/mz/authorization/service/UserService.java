package com.mz.authorization.service;

import com.mz.authorization.config.MongoConfig;
import com.mz.authorization.form.UserForm;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import com.mz.authorization.response.JwtResponse;
import com.mz.authorization.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Transactional
@Service
public class UserService extends DefaultService<User, UserRepository, UserForm, UserResponse> {

    private SecureService secureService;

    @Autowired
    UserService(UserRepository repository, SecureService secureService) {
        super(repository);
        this.secureService = secureService;
    }

    public Mono<UserResponse> getByCredentials(UserForm form) {
        return repository.findUserByEmailAndPasswordAndActive(form.getEmail(), form.getPassword(), true)
                .map(this::convertEntityToResponse);
    }

    public Mono<JwtResponse> authenticate(UserForm form) {
        return getByCredentials(form)
                .defaultIfEmpty(UserResponse.builder().build())
                .map(secureService::createJwtResponse);
    }

    public Mono<UserResponse> check(String token) {
        UserResponse user = secureService.detokenise(token);
        if (user != null) {
            return repository.findUserByEmailAndActive(user.getEmail(), user.isActive())
                    .map(this::convertEntityToResponse);
        }
        return Mono.empty();
    }


    /**
     * used only in dev by {@link com.mz.authorization.config.MongoConfig}
     * */
    public void initiateDb() {
        log.info("init db");
        repository.deleteAll()
                .subscribe();
        save(User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .build(), "0")
            .subscribe(u -> log.info("user inserted", u));
    }
}
