package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.entity.Token;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.repository.EnterpriseRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class SecondCommunicationWithOrganizationAPIImpl implements CommunicationWithOrganizationAPI {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseDataRepository enterpriseDataRepository;

    @Override
    public Mono<Token> login(LoginRequest loginRequest) {
        throw new RuntimeException("Failed to authenticate");
    }

    @Override
    public Mono<Enterprise> createEnterprise(CreateEnterpriseRequest request) {
        Enterprise enterprise = Enterprise.builder()
                .organizationId(UUID.randomUUID())
                .longName(request.getLongName())
                .shortName(request.getShortName())
                .email(request.getEmail())
                .description(request.getDescription())
                .businessDomains(request.getBusinessDomains())
                .logoUrl(request.getLogoUrl())
                .type(request.getType())
                .websiteUrl(request.getWebsiteUrl())
                .socialNetwork(request.getSocialNetwork())
                .businessRegistrationNumber(request.getBusinessRegistrationNumber())
                .taxNumber(request.getTaxNumber())
                .capitalShare(request.getCapitalShare())
                .registrationDate(request.getRegistrationDate())
                .ceoName(request.getCeoName())
                .orgContact(request.getOrgContact())
                .businessActorId(request.getBusinessActorId() == null ? UUID.randomUUID() : request.getBusinessActorId())
                .status(request.getStatus())
                .yearFounded(request.getYearFounded())
                .keywords(request.getKeywords())
                .numberOfEmployees(request.getNumberOfEmployees())
                .build();
        log.info("Creating enterprise: {}", enterprise);
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Mono<Enterprise> updateEnterprise(Enterprise enterprise) {
        return enterpriseRepository.findById(enterprise.getOrganizationId())
                .flatMap(updatedEnterprise -> {
                    updatedEnterprise.setLongName(enterprise.getLongName());
                    updatedEnterprise.setShortName(enterprise.getShortName());
                    updatedEnterprise.setEmail(enterprise.getEmail());
                    updatedEnterprise.setDescription(enterprise.getDescription());
                    updatedEnterprise.setBusinessDomains(enterprise.getBusinessDomains());
                    updatedEnterprise.setLogoUrl(enterprise.getLogoUrl());
                    updatedEnterprise.setType(enterprise.getType());
                    updatedEnterprise.setWebsiteUrl(enterprise.getWebsiteUrl());
                    updatedEnterprise.setSocialNetwork(enterprise.getSocialNetwork());
                    updatedEnterprise.setBusinessRegistrationNumber(enterprise.getBusinessRegistrationNumber());
                    updatedEnterprise.setTaxNumber(enterprise.getTaxNumber());
                    updatedEnterprise.setCapitalShare(enterprise.getCapitalShare());
                    updatedEnterprise.setRegistrationDate(enterprise.getRegistrationDate());
                    updatedEnterprise.setCeoName(enterprise.getCeoName());
                    updatedEnterprise.setYearFounded(enterprise.getYearFounded());
                    updatedEnterprise.setKeywords(enterprise.getKeywords());
                    updatedEnterprise.setNumberOfEmployees(enterprise.getNumberOfEmployees());
                    return enterpriseRepository.save(updatedEnterprise);
                })
                .onErrorResume(e -> {
                    log.error("Error updating enterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to update enterprise"));
                });



    }

    @Override
    public Mono<Void> deleteEnterprise(Enterprise enterprise) {
        if (enterprise == null || enterprise.getOrganizationId() == null)
            return Mono.error(new RuntimeException("Enterprise not found"));
        return enterpriseRepository.deleteById(enterprise.getOrganizationId());
    }

    @Override
    public Mono<Enterprise> getEnterpriseById(UUID id) {
        return enterpriseRepository.findById(id)
                .onErrorResume(e -> Mono.error(new RuntimeException("Enterprise not found")));
    }

    @Override
    public Flux<Enterprise> getAllEnterprise() {
        return enterpriseRepository.findAll();
    }
}