package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Certification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CertificationService {
    Flux<Certification> findAll();
    Mono<Certification> findById(UUID id);
    Mono<Certification> save(Certification certification);
    Mono<Void> deleteById(UUID id);
}

