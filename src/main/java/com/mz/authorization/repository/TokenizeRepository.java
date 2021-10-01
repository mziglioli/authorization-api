package com.mz.authorization.repository;

import com.mz.authorization.model.Tokenize;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenizeRepository extends ReactiveMongoRepository<Tokenize, String> {

    Mono<Tokenize> findTokenizeByEmail(String email);
}
