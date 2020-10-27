package com.mz.authorization.repository;

import com.mz.authorization.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findUserByEmailAndPasswordAndActive(String email, String password, boolean active);
    Mono<User> findUserByEmailAndActive(String email, boolean active);

}
