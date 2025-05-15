package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.repository.EnterpriseDataRepository;
import lombok.Getter;

import java.util.UUID;

public class EnterpriseDataComparator implements Comparable<Enterprise> {
    @Getter
    private final Enterprise enterprise;

    private final EnterpriseDataRepository dataRepository;

    public EnterpriseDataComparator(Enterprise enterprise, EnterpriseDataRepository dataRepository) {
        this.enterprise = enterprise;
        this.dataRepository = dataRepository;
    }

    @Override
    public int compareTo(Enterprise o) {
        EnterpriseData enterpriseData = dataRepository.findById(enterprise.getOrganizationId()).orElse(null);
        if (enterpriseData == null) {
            enterpriseData.setEnterpriseId(UUID.randomUUID());
            enterpriseData.setEnterpriseId(enterprise.getOrganizationId());
            enterpriseData.setViewsNumbers(1L);
            dataRepository.save(enterpriseData);
        }
        EnterpriseData oData = dataRepository.findById(o.getOrganizationId()).orElse(null);
        if (oData == null) {
            oData = new EnterpriseData();
            oData.setEnterpriseId(UUID.randomUUID());
            oData.setEnterpriseId(o.getOrganizationId());
            oData.setViewsNumbers(1L);
            dataRepository.save(oData);
        }
        return (int) (enterpriseData.getViewsNumbers() - oData.getViewsNumbers());
    }
}