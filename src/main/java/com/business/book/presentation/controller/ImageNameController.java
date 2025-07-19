package com.business.book.presentation.controller;

import com.business.book.domain.service.ImageNameService;
import com.business.book.infrastructure.entity.ImageName;
import com.business.book.infrastructure.repository.ImageNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/image-names")
@RequiredArgsConstructor
public class ImageNameController {
    private final ImageNameService imageNameService;
    private final ImageNameRepository imageNameRepository;

    @GetMapping
    public Flux<ImageName> getAll() {
        return imageNameService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ImageName>> getById(@PathVariable UUID id) {
        return imageNameService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ImageName> create(@RequestBody ImageName imageName) {
        return imageNameService.save(imageName);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return imageNameService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return imageNameRepository.findByOrganizationId(organizationId);
    }
}

