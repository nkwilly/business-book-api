package ink.yowyob.business.book.domain.service.impl;

import ink.yowyob.business.book.domain.exception.AuthenticationException;
import ink.yowyob.business.book.domain.exception.AuthoritiesException;
import ink.yowyob.business.book.domain.mapper.EnterpriseMapper;
import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.EnterpriseData;
import ink.yowyob.business.book.infrastructure.entity.Token;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseRepository;
import ink.yowyob.business.book.domain.service.CommunicationWithOrganizationAPI;
import ink.yowyob.business.book.presentation.dto.enterprise.CreateEnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.user.LoginDto;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
//@Primary
@Service
@RequiredArgsConstructor
public class SecondCommunicationWithOrganizationAPIImpl implements CommunicationWithOrganizationAPI {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseDataRepository enterpriseDataRepository;
    private final EnterpriseMapper enterpriseMapper;

    @Override
    public Mono<Token> login(LoginDto LoginDto) {
        throw new AuthenticationException("Failed to login, please check your credentials");
    }

    @Override
    public Mono<Token> register(RegisterDto RegisterDto) {
        throw new AuthenticationException("Failed to register");
    }

    @Override
    public Mono<Enterprise> createEnterprise(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Mono<Enterprise> updateEnterprise(UpdateEnterpriseDto dto) {
        if (dto == null || dto.getOrganizationId() == null) {
            throw new IllegalArgumentException("Enterprise or organization ID cannot be null");
        }
        Enterprise updatedEnterprise = enterpriseMapper.toEnterprise(dto);
        return enterpriseRepository.save(updatedEnterprise);
    }

    @Override
    public Mono<Void> deleteEnterprise(UUID uuid) {
        Mono<Enterprise> enterprise = enterpriseRepository.findById(uuid);
        return enterprise.map(enterpriseRepository::delete).then();
    }

    @Override
    public Mono<Enterprise> getEnterpriseById(UUID id) {
        return enterpriseRepository.findById(id);
    }

    @Override
    public Flux<Enterprise> getAllEnterprise() {
        return enterpriseRepository.findAll();
    }
}