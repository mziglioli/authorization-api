package com.mz.authorization.config;

import com.mz.authorization.model.User;
import com.mz.authorization.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("dev")
@Slf4j
@Configuration
public class MongoConfig {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initDb() {
        userRepository.deleteAll().subscribe(r -> log.info("deleting db", r));
        userRepository.insert(User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .build())
            .subscribe(userAuth -> log.info("Entity has been saved: {}", userAuth));
    }
}
