package com.mz.authorization.service;

import com.mz.authorization.form.DefaultForm;
import com.mz.authorization.model.EntityJpa;
import com.mz.authorization.response.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
public abstract class DefaultService <T extends EntityJpa, R extends ReactiveMongoRepository<T, String>, F extends DefaultForm, Response extends DefaultResponse> {

    protected R repository;

    DefaultService(R repository) {
        this.repository = repository;
    }

    protected T convertFormToEntity(F form) {
        return (T) form.convertToEntity();
    }
    protected Response convertEntityToResponse(T entity) {
        if (entity == null) {
            return (Response) new DefaultResponse("", false);
        }
        return (Response) entity.convert();
    }

    Mono<T> save(F form, String userId) {
        return save(convertFormToEntity(form), userId);
    }

    Mono<T> save(T entity, String userId) {
        if (null != entity.getId()) {
            return update(entity, userId);
        } else {
            beforeInsert(entity, userId);
            return repository.save(entity);
        }
    }

    Mono<T> update(T entity, String userId) {
        beforeUpdate(entity, userId);
        return repository.save(entity);
    }

    void awaitUpdate(T entity, String userId) {
        beforeUpdate(entity, userId);
        repository.save(entity)
                .subscribe(u -> log.info("user updated", u));
    }

    Mono<T> delete(T entity, String userId) {
        beforeDelete(entity, userId);
        return repository.save(entity);
    }

    void beforeInsert(T entity, String userId) {
        entity.setCreatedBy(userId);
        entity.setCreatedDate(getTime());
        entity.setActive(true);
    }
    void beforeUpdate(T entity, String userId) {
        entity.setUpdatedBy(userId);
        entity.setUpdatedDate(getTime());
        entity.setActive(true);
    }
    void beforeDelete(T entity, String userId) {
        beforeUpdate(entity, userId);
        entity.setActive(false);
    }
    public String getAuthenticatedUserId() {
        return "0";
    }
    LocalDateTime getTime() {
        return LocalDateTime.now();
    }
    public Mono<Response> getById(String id) {
        return repository.findById(id)
                .map(this::convertEntityToResponse);
    }
    public Flux<Response> getAll() {
        return repository.findAll()
                .map(this::convertEntityToResponse);
    }
    public Mono<Response> add(F form) {
        return save(form, getAuthenticatedUserId())
                .map(this::convertEntityToResponse);
    }
    public Mono<Response> update(F form) {
        return save(form, getAuthenticatedUserId())
                .map(this::convertEntityToResponse);
    }
    public Mono<Response> delete(String id) {
        return repository.findById(id)
            .flatMap(user -> delete(user, getAuthenticatedUserId())
            .map(this::convertEntityToResponse));
    }
}
