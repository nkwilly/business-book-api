package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.repository.EnterpriseDataRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnterpriseDataComparator implements Comparable<Enterprise> {
    @Getter @Setter
    private Enterprise enterprise;

    @Autowired
    private EnterpriseDataRepository dataRepository;

    public EnterpriseDataComparator(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public int compareTo(Enterprise o) {
        EnterpriseData enterpriseData = dataRepository.findById(enterprise.getId()).orElse(null);
        if (enterpriseData == null) {
            enterpriseData = new EnterpriseData();
            enterpriseData.setId(UUID.randomUUID());
            enterpriseData.setEnterpriseId(enterprise.getId());
            enterpriseData.setViewsNumbers(1L);
            dataRepository.save(enterpriseData);
        }
        EnterpriseData oData = dataRepository.findById(o.getId()).orElse(null);
        if (oData == null) {
            oData = new EnterpriseData();
            oData.setId(UUID.randomUUID());
            oData.setEnterpriseId(o.getId());
            oData.setViewsNumbers(1L);
            dataRepository.save(oData);
        }
        return (int) (enterpriseData.getViewsNumbers() - oData.getViewsNumbers());
    }
}