package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Enterprise;
import com.business.book.infrastructure.entity.Token;
import com.business.book.presentation.dto.CreateEnterpriseDto;
import com.business.book.presentation.dto.LoginDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CommunicationWithOrganizationAPI {
    Mono<Token> login(LoginDto loginRequest);

    Mono<Enterprise> createEnterprise(CreateEnterpriseDto enterprise);

    Mono<Enterprise> updateEnterprise(Enterprise enterprise);

    Mono<Void> deleteEnterprise(UUID enterpriseId);

    Mono<Enterprise> getEnterpriseById(UUID id);

    Flux<Enterprise> getAllEnterprise();

    void updateAllEnterprisesInDatabase();
}
