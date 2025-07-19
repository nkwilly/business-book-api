package com.business.book.domain.service;

import com.business.book.infrastructure.entity.ImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ImageNameService {
    Flux<ImageName> findAll();
    Mono<ImageName> findById(UUID id);
    Mono<ImageName> save(ImageName imageName);
    Mono<Void> deleteById(UUID id);
}

