package com.business.book.presentation.controller;

import com.business.book.domain.service.AgencyService;
import com.business.book.infrastructure.entity.Agency;
import com.business.book.infrastructure.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/agencies")
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyService agencyService;
    private final AgencyRepository agencyRepository;

    @GetMapping
    public Flux<Agency> getAll() {
        return agencyService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Agency>> getById(@PathVariable UUID id) {
        return agencyService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Agency> create(@RequestBody Agency agency) {
        return agencyService.save(agency);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return agencyService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return agencyRepository.findByOrganizationId(organizationId);
    }
}

