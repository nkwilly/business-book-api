package com.business.book.presentation.controller;

import com.business.book.domain.service.CertificationService;
import com.business.book.infrastructure.entity.Certification;
import com.business.book.infrastructure.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;
    private final CertificationRepository certificationRepository;

    @GetMapping
    public Flux<Certification> getAll() {
        return certificationService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Certification>> getById(@PathVariable UUID id) {
        return certificationService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Certification> create(@RequestBody Certification certification) {
        return certificationService.save(certification);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return certificationService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return certificationRepository.findByOrganizationId(organizationId);
    }
}

