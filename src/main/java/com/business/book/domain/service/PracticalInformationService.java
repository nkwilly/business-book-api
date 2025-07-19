package com.business.book.domain.service;

import com.business.book.infrastructure.entity.PracticalInformation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PracticalInformationService {
    Flux<PracticalInformation> findAll();

    Mono<PracticalInformation> findById(UUID id);

    Mono<PracticalInformation> save(PracticalInformation agency);

    Mono<Void> deleteById(UUID id);
}
