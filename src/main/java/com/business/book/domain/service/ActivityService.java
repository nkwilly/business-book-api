package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Activity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ActivityService {
    Flux<Activity> findAll();

    Mono<Activity> findById(UUID id);

    Mono<Activity> save(Activity activity);

    Mono<Void> deleteById(UUID id);
}
