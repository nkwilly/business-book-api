package ink.yowyob.business.book.domain.mapper.impl;

import ink.yowyob.business.book.domain.mapper.EnterpriseMapper;
import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.presentation.dto.enterprise.CreateEnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseMapperImpl implements EnterpriseMapper {

    @Override
    public CreateEnterpriseDto toCreateEnterpriseDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }

        CreateEnterpriseDto createEnterpriseDto = new CreateEnterpriseDto();
        createEnterpriseDto.setLongName(enterprise.getLongName());
        createEnterpriseDto.setShortName(enterprise.getShortName());
        createEnterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        createEnterpriseDto.setIndividualBusiness(enterprise.isIndividualBusiness());
        createEnterpriseDto.setDescription(enterprise.getDescription());
        createEnterpriseDto.setType(enterprise.getType());
        createEnterpriseDto.setActive(enterprise.isActive());
        createEnterpriseDto.setWebsiteUrl(enterprise.getWebsiteUrl());
        createEnterpriseDto.setBusinessRegistrationNumber(enterprise.getBusinessRegistrationNumber());
        createEnterpriseDto.setTaxNumber(enterprise.getTaxNumber());
        createEnterpriseDto.setCapitalShare(enterprise.getCapitalShare());
        createEnterpriseDto.setRegistrationDate(enterprise.getRegistrationDate());
        createEnterpriseDto.setCeoName(enterprise.getCeoName());
        createEnterpriseDto.setYearFounded(enterprise.getYearFounded());
        createEnterpriseDto.setKeywords(enterprise.getKeywords());
        createEnterpriseDto.setStatus(enterprise.getStatus());
        createEnterpriseDto.setBusinessDomains(enterprise.getBusinessDomains());
        createEnterpriseDto.setOrgContact(enterprise.getOrgContact());
        createEnterpriseDto.setEmail(enterprise.getEmail());
        createEnterpriseDto.setSocialNetwork(enterprise.getSocialNetwork());
        createEnterpriseDto.setNumberOfEmployees(enterprise.getNumberOfEmployees());
        createEnterpriseDto.setBusinessActorId(enterprise.getBusinessActorId());
        return createEnterpriseDto;
    }

    @Override
    public UpdateEnterpriseDto toUpdateEnterpriseDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }

        UpdateEnterpriseDto updateEnterpriseDto = new UpdateEnterpriseDto();
        updateEnterpriseDto.setOrganizationId(enterprise.getOrganizationId());
        updateEnterpriseDto.setLongName(enterprise.getLongName());
        updateEnterpriseDto.setShortName(enterprise.getShortName());
        updateEnterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        updateEnterpriseDto.setIndividualBusiness(enterprise.isIndividualBusiness());
        updateEnterpriseDto.setDescription(enterprise.getDescription());
        updateEnterpriseDto.setType(enterprise.getType());
        updateEnterpriseDto.setActive(enterprise.isActive());
        updateEnterpriseDto.setWebsiteUrl(enterprise.getWebsiteUrl());
        updateEnterpriseDto.setBusinessRegistrationNumber(enterprise.getBusinessRegistrationNumber());
        updateEnterpriseDto.setTaxNumber(enterprise.getTaxNumber());
        updateEnterpriseDto.setCapitalShare(enterprise.getCapitalShare());
        updateEnterpriseDto.setRegistrationDate(enterprise.getRegistrationDate());
        updateEnterpriseDto.setCeoName(enterprise.getCeoName());
        updateEnterpriseDto.setYearFounded(enterprise.getYearFounded());
        updateEnterpriseDto.setKeywords(enterprise.getKeywords());
        updateEnterpriseDto.setStatus(enterprise.getStatus());
        updateEnterpriseDto.setBusinessDomains(enterprise.getBusinessDomains());
        updateEnterpriseDto.setOrgContact(enterprise.getOrgContact());
        updateEnterpriseDto.setEmail(enterprise.getEmail());
        updateEnterpriseDto.setSocialNetwork(enterprise.getSocialNetwork());
        updateEnterpriseDto.setNumberOfEmployees(enterprise.getNumberOfEmployees());
        updateEnterpriseDto.setBusinessActorId(enterprise.getBusinessActorId());
        return updateEnterpriseDto;
    }

    @Override
    public EnterpriseDto toEnterpriseDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }

        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setOrganizationId(enterprise.getOrganizationId());
        enterpriseDto.setLongName(enterprise.getLongName());
        enterpriseDto.setShortName(enterprise.getShortName());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        enterpriseDto.setIndividualBusiness(enterprise.isIndividualBusiness());
        enterpriseDto.setDescription(enterprise.getDescription());
        enterpriseDto.setType(enterprise.getType());
        enterpriseDto.setActive(enterprise.isActive());
        enterpriseDto.setWebsiteUrl(enterprise.getWebsiteUrl());
        enterpriseDto.setBusinessRegistrationNumber(enterprise.getBusinessRegistrationNumber());
        enterpriseDto.setTaxNumber(enterprise.getTaxNumber());
        enterpriseDto.setCapitalShare(enterprise.getCapitalShare());
        enterpriseDto.setRegistrationDate(enterprise.getRegistrationDate());
        enterpriseDto.setCeoName(enterprise.getCeoName());
        enterpriseDto.setYearFounded(enterprise.getYearFounded());
        enterpriseDto.setKeywords(enterprise.getKeywords());
        enterpriseDto.setStatus(enterprise.getStatus());
        enterpriseDto.setBusinessDomains(enterprise.getBusinessDomains());
        enterpriseDto.setOrgContact(enterprise.getOrgContact());
        enterpriseDto.setEmail(enterprise.getEmail());
        enterpriseDto.setSocialNetwork(enterprise.getSocialNetwork());
        enterpriseDto.setNumberOfEmployees(enterprise.getNumberOfEmployees());
        enterpriseDto.setBusinessActorId(enterprise.getBusinessActorId());
        return enterpriseDto;
    }

    @Override
    public Enterprise toEnterprise(CreateEnterpriseDto createEnterpriseDto) {
        if (createEnterpriseDto == null) {
            return null;
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setLongName(createEnterpriseDto.getLongName());
        enterprise.setShortName(createEnterpriseDto.getShortName());
        enterprise.setLogoUrl(createEnterpriseDto.getLogoUrl());
        enterprise.setIndividualBusiness(createEnterpriseDto.isIndividualBusiness());
        enterprise.setDescription(createEnterpriseDto.getDescription());
        enterprise.setType(createEnterpriseDto.getType());
        enterprise.setActive(createEnterpriseDto.isActive());
        enterprise.setWebsiteUrl(createEnterpriseDto.getWebsiteUrl());
        enterprise.setBusinessRegistrationNumber(createEnterpriseDto.getBusinessRegistrationNumber());
        enterprise.setTaxNumber(createEnterpriseDto.getTaxNumber());
        enterprise.setCapitalShare(createEnterpriseDto.getCapitalShare());
        enterprise.setRegistrationDate(createEnterpriseDto.getRegistrationDate());
        enterprise.setCeoName(createEnterpriseDto.getCeoName());
        enterprise.setYearFounded(createEnterpriseDto.getYearFounded());
        enterprise.setKeywords(createEnterpriseDto.getKeywords());
        enterprise.setStatus(createEnterpriseDto.getStatus());
        enterprise.setBusinessDomains(createEnterpriseDto.getBusinessDomains());
        enterprise.setOrgContact(createEnterpriseDto.getOrgContact());
        enterprise.setEmail(createEnterpriseDto.getEmail());
        enterprise.setSocialNetwork(createEnterpriseDto.getSocialNetwork());
        enterprise.setNumberOfEmployees(createEnterpriseDto.getNumberOfEmployees());
        enterprise.setBusinessActorId(createEnterpriseDto.getBusinessActorId());
        return enterprise;
    }

    @Override
    public Enterprise toEnterprise(UpdateEnterpriseDto updateEnterpriseDto) {
        if (updateEnterpriseDto == null) {
            return null;
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setOrganizationId(updateEnterpriseDto.getOrganizationId());
        enterprise.setLongName(updateEnterpriseDto.getLongName());
        enterprise.setShortName(updateEnterpriseDto.getShortName());
        enterprise.setLogoUrl(updateEnterpriseDto.getLogoUrl());
        enterprise.setIndividualBusiness(updateEnterpriseDto.isIndividualBusiness());
        enterprise.setDescription(updateEnterpriseDto.getDescription());
        enterprise.setType(updateEnterpriseDto.getType());
        enterprise.setActive(updateEnterpriseDto.isActive());
        enterprise.setWebsiteUrl(updateEnterpriseDto.getWebsiteUrl());
        enterprise.setBusinessRegistrationNumber(updateEnterpriseDto.getBusinessRegistrationNumber());
        enterprise.setTaxNumber(updateEnterpriseDto.getTaxNumber());
        enterprise.setCapitalShare(updateEnterpriseDto.getCapitalShare());
        enterprise.setRegistrationDate(updateEnterpriseDto.getRegistrationDate());
        enterprise.setCeoName(updateEnterpriseDto.getCeoName());
        enterprise.setYearFounded(updateEnterpriseDto.getYearFounded());
        enterprise.setKeywords(updateEnterpriseDto.getKeywords());
        enterprise.setStatus(updateEnterpriseDto.getStatus());
        enterprise.setBusinessDomains(updateEnterpriseDto.getBusinessDomains());
        enterprise.setOrgContact(updateEnterpriseDto.getOrgContact());
        enterprise.setEmail(updateEnterpriseDto.getEmail());
        enterprise.setSocialNetwork(updateEnterpriseDto.getSocialNetwork());
        enterprise.setNumberOfEmployees(updateEnterpriseDto.getNumberOfEmployees());
        enterprise.setBusinessActorId(updateEnterpriseDto.getBusinessActorId());
        return enterprise;
    }

    @Override
    public Enterprise toEnterprise(EnterpriseDto dto) {
        System.err.println("\n\n\n dto = " + dto + "\n\n\n");

        if (dto == null) {
            return null;
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setOrganizationId(dto.getOrganizationId());
        enterprise.setLongName(dto.getLongName());
        enterprise.setShortName(dto.getShortName());
        enterprise.setLogoUrl(dto.getLogoUrl());
        enterprise.setIndividualBusiness(dto.isIndividualBusiness());
        enterprise.setDescription(dto.getDescription());
        enterprise.setType(dto.getType());
        enterprise.setActive(dto.isActive());
        enterprise.setWebsiteUrl(dto.getWebsiteUrl());
        enterprise.setBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
        enterprise.setTaxNumber(dto.getTaxNumber());
        enterprise.setCapitalShare(dto.getCapitalShare());
        enterprise.setRegistrationDate(dto.getRegistrationDate());
        enterprise.setCeoName(dto.getCeoName());
        enterprise.setYearFounded(dto.getYearFounded());
        enterprise.setKeywords(dto.getKeywords());
        enterprise.setStatus(dto.getStatus());
        enterprise.setBusinessDomains(dto.getBusinessDomains());
        enterprise.setOrgContact(dto.getOrgContact());
        enterprise.setEmail(dto.getEmail());
        enterprise.setSocialNetwork(dto.getSocialNetwork());
        enterprise.setNumberOfEmployees(dto.getNumberOfEmployees());
        enterprise.setBusinessActorId(dto.getBusinessActorId());
        return enterprise;
    }
}
