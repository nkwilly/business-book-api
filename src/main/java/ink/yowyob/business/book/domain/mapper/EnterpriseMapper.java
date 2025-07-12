package ink.yowyob.business.book.domain.mapper;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.presentation.dto.enterprise.CreateEnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;

public interface EnterpriseMapper {
    
    CreateEnterpriseDto toCreateEnterpriseDto(Enterprise enterprise);

    UpdateEnterpriseDto toUpdateEnterpriseDto(Enterprise enterprise);

    EnterpriseDto toEnterpriseDto(Enterprise enterprise);

    Enterprise toEnterprise(CreateEnterpriseDto createEnterpriseDto);

    Enterprise toEnterprise(UpdateEnterpriseDto updateEnterpriseDto);

    Enterprise toEnterprise(EnterpriseDto dto);
}
