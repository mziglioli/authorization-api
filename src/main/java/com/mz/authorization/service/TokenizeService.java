package com.mz.authorization.service;

import com.mz.authorization.form.TokenizeForm;
import com.mz.authorization.model.Tokenize;
import com.mz.authorization.model.User;
import com.mz.authorization.repository.TokenizeRepository;
import com.mz.authorization.response.TokenizeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Transactional
@Service
public class TokenizeService extends DefaultService<Tokenize, TokenizeRepository, TokenizeForm, TokenizeResponse> {

    private UserService userService;

    @Autowired
    TokenizeService(TokenizeRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    public String generateToken() {
        return "1234";
    }

    public Mono<Tokenize> getTokenizeByEmail(String email) {
        return repository.findTokenizeByEmail(email).defaultIfEmpty(new Tokenize());
    }

    public Mono<TokenizeResponse> resetPassword(TokenizeForm form) {
        log.info("resetPassword: pending");
        return Mono.zip(userService.findByEmail(form.getEmail()), getTokenizeByEmail(form.getEmail()))
                .map(objects -> {
                    log.info("resetPassword: objects");
                    User user = objects.getT1();
                    Tokenize tokenize = objects.getT2();
                    boolean success = false;
                    log.info("resetPassword: " + user.toString() + " - " + tokenize.toString());
                    if (isNotEmpty(tokenize.getId())) {
                        log.info("resetPassword: token: found");
                        tokenize.setToken(generateToken());
                        awaitUpdate(tokenize, getAuthenticatedUserId());
                        log.info("resetPassword: token: updated");
                        success = true;
                    } else if(isNotEmpty(user.getId())) {
                        log.info("resetPassword: token: empty");
                        awaitSave(Tokenize.builder()
                                .email(user.getEmail())
                                .token(generateToken())
                                .build(), getAuthenticatedUserId());
                        log.info("resetPassword: token: created");
                        success = true;
                    }
                    log.info("resetPassword: finish:" + success);
                    return TokenizeResponse.builder().email(form.getEmail()).success(success).build();
                });
    }
}
