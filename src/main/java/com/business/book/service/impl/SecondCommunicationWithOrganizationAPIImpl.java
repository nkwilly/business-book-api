package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.entity.Token;
import com.business.book.entity.constants.Type;
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

import java.util.ArrayList;
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
    public Token login(LoginRequest loginRequest) {
        throw new RuntimeException("Failed to authenticate");
    }

    @Override
    public Token register(RegisterRequest registerRequest) {
        throw new RuntimeException("Failed to register");
    }

    @Override
    public Enterprise createEnterprise(CreateEnterpriseRequest request) {
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
    public Enterprise updateEnterprise(Enterprise enterprise) {
        Enterprise updatedEnterprise = enterpriseRepository.findById(enterprise.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Enterprise not found"));
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
    }

    @Override
    public boolean deleteEnterprise(Enterprise enterprise) {
        if (enterprise == null || enterprise.getOrganizationId() == null)
            return false;
        try {
            enterpriseRepository.deleteById(enterprise.getOrganizationId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Enterprise getEnterpriseById(UUID id) {
        Enterprise enterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enterprise not found"));
        enterpriseDataRepository.incrementView(id);
        return enterpriseRepository.findById(id).orElseThrow(() -> new RuntimeException("Enterprise not found"));
    }

    @Override
    public List<Enterprise> getAllEnterprise() {
        List<Enterprise> enterprises = enterpriseRepository.findAll();

        Map<UUID, Long> enterpriseViewsMap = new HashMap<>();

        enterprises.forEach(enterprise -> {
            EnterpriseData data = enterpriseDataRepository.findByEnterpriseId(enterprise.getOrganizationId())
                    .orElse(new EnterpriseData(enterprise.getOrganizationId(), 0L, false));

            // Stocker le nombre de vues dans la map
            enterpriseViewsMap.put(enterprise.getOrganizationId(), data.getViewsNumbers());
        });

        enterprises.sort((e1, e2) -> {
            Long views1 = enterpriseViewsMap.get(e1.getOrganizationId());
            Long views2 = enterpriseViewsMap.get(e2.getOrganizationId());

            return Long.compare(views2, views1);
        });

        return enterprises;
    }
}