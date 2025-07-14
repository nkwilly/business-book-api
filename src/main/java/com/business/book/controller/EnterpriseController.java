package com.business.book.controller;

import com.business.book.entity.Enterprise;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final CommunicationWithOrganizationAPI communicationAPI;
    private final EnterpriseDataRepository dataRepository;



    @PostMapping
    public Mono<Enterprise> createEnterprise(@RequestBody @Valid CreateEnterpriseRequest enterprise) {
        return communicationAPI.createEnterprise(enterprise);
    }

    @PutMapping
    public Mono<Enterprise> updateEnterprise(@RequestBody Enterprise enterprise) {
        return communicationAPI.updateEnterprise(enterprise);
    }

    @DeleteMapping
    public Mono<Void> deleteEnterprise(@RequestBody Enterprise enterprise) {
        return communicationAPI.deleteEnterprise(enterprise);
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
