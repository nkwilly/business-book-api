package com.business.book.domain.service.impl;

import com.business.book.domain.service.ImageNameService;
import com.business.book.infrastructure.entity.ImageName;
import com.business.book.infrastructure.repository.ImageNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageNameServiceImpl implements ImageNameService {
    private final ImageNameRepository imageNameRepository;

    public Flux<ImageName> findAll() {
        return imageNameRepository.findAll();
    }

    public Mono<ImageName> findById(UUID id) {
        return imageNameRepository.findById(id);
    }

    public Mono<ImageName> save(ImageName imageName) {
        return imageNameRepository.save(imageName);
    }

    public Mono<Void> deleteById(UUID id) {
        return imageNameRepository.deleteById(id);
    }
}
