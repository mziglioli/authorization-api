package com.mz.authorization.config;

import com.mz.authorization.service.UserService;
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
    UserService userService;

    @PostConstruct
    public void initDb() {
        userService.initiateDb();
    }
}
