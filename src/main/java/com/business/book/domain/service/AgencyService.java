package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Agency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface AgencyService {
    Flux<Agency> findAll();
    Mono<Agency> findById(UUID id);
    Mono<Agency> save(Agency agency);
    Mono<Void> deleteById(UUID id);
}

