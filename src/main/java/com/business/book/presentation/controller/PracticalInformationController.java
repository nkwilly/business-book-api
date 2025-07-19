package com.business.book.presentation.controller;

import com.business.book.domain.service.PracticalInformationService;
import com.business.book.infrastructure.entity.PracticalInformation;
import com.business.book.infrastructure.repository.PracticalInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/practical-informations")
@RequiredArgsConstructor
public class PracticalInformationController {
    private final PracticalInformationService practicalInformationService;
    private final PracticalInformationRepository practicalInformationRepository;

    @GetMapping
    public Flux<PracticalInformation> getAll() {
        return practicalInformationService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PracticalInformation>> getById(@PathVariable UUID id) {
        return practicalInformationService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<PracticalInformation> create(@RequestBody PracticalInformation info) {
        return practicalInformationService.save(info);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return practicalInformationService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return practicalInformationRepository.findByOrganizationId(organizationId);
    }
}