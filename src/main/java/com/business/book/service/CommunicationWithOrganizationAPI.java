package com.business.book.service;

import com.business.book.entity.Enterprise;
import com.business.book.entity.Token;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CommunicationWithOrganizationAPI {
    Mono<Token> login(LoginRequest loginRequest);

    Mono<Enterprise> createEnterprise(CreateEnterpriseRequest enterprise);

    Mono<Enterprise> updateEnterprise(Enterprise enterprise);

    Mono<Void> deleteEnterprise(Enterprise enterprise);

    Mono<Enterprise> getEnterpriseById(UUID id);

    Flux<Enterprise> getAllEnterprise();
}
