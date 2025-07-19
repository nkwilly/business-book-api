package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Prospect;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProspectService {
    Flux<Prospect> findAll();

    Mono<Prospect> findById(UUID id);

    Mono<Prospect> save(Prospect prospect);

    Mono<Void> deleteById(UUID id);
}