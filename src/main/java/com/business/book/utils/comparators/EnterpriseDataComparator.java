package com.business.book.utils.comparators;

import com.business.book.infrastructure.entity.Enterprise;
import com.business.book.infrastructure.repository.EnterpriseDataRepository;
import lombok.Getter;

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
        return 0;
    }
    /*
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

     */
}