package ink.yowyob.business.book.domain.service;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.Token;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.user.LoginDto;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.presentation.dto.enterprise.CreateEnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CommunicationWithOrganizationAPI {
    Mono<Token> login(LoginDto LoginDto);

    Mono<Token> register(RegisterDto RegisterDto);

    Mono<Enterprise> createEnterprise(Enterprise enterprise);

    Mono<Enterprise> updateEnterprise(UpdateEnterpriseDto enterprise);

    Mono<Void> deleteEnterprise(UUID uuid);

    Mono<Enterprise> getEnterpriseById(UUID id);

    Flux<Enterprise> getAllEnterprise();
}
