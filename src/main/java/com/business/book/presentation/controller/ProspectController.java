package com.business.book.presentation.controller;

import com.business.book.domain.service.ProspectService;
import com.business.book.infrastructure.entity.Prospect;
import com.business.book.infrastructure.repository.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/prospects")
@RequiredArgsConstructor
public class ProspectController {
    private final ProspectService prospectService;
    private final ProspectRepository prospectRepository;

    @GetMapping
    public Flux<Prospect> getAll() {
        return prospectService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Prospect>> getById(@PathVariable UUID id) {
        return prospectService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Prospect> create(@RequestBody Prospect prospect) {
        return prospectService.save(prospect);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return prospectService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return prospectRepository.findByOrganizationId(organizationId);
    }
}

