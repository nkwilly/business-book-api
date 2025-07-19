package com.business.book.presentation.controller;

import com.business.book.infrastructure.entity.Enterprise;
import com.business.book.infrastructure.repository.EnterpriseDataRepository;
import com.business.book.infrastructure.repository.EnterpriseRepository;
import com.business.book.domain.service.CommunicationWithOrganizationAPI;
import com.business.book.presentation.dto.CreateEnterpriseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final CommunicationWithOrganizationAPI communicationAPI;
    private final EnterpriseDataRepository dataRepository;
    private final EnterpriseRepository enterpriseRepository;


    @PostMapping
    public Mono<Enterprise> createEnterprise(@RequestBody @Valid CreateEnterpriseDto enterprise) {
        return communicationAPI.createEnterprise(enterprise);
    }

    @PutMapping
    public Mono<Enterprise> updateEnterprise(@RequestBody Enterprise enterprise) {
        return communicationAPI.updateEnterprise(enterprise);
    }

    @DeleteMapping
    public Mono<Void> deleteEnterprise(@RequestBody UUID enterpriseId) {
        return enterpriseRepository.findById(enterpriseId)
                .map(Enterprise::getOrganizationId)
                .flatMap(communicationAPI::deleteEnterprise);
    }

    @GetMapping("/{id}")
    public Mono<Enterprise> getEnterpriseById(@PathVariable UUID id) {
        return communicationAPI.getEnterpriseById(id);
    }

    @GetMapping
    public Flux<Enterprise> getAllEnterprises() {
        return communicationAPI.getAllEnterprise();
    }
}
