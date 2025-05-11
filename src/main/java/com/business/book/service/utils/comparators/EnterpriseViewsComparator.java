package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.repository.EnterpriseDataRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

public class EnterpriseDataComparator implements Comparable<Enterprise> {
    @Getter
    @Setter
    private final Enterprise enterprise;

    private final EnterpriseDataRepository dataRepository;

    public EnterpriseDataComparator(Enterprise enterprise, EnterpriseDataRepository dataRepository) {
        this.enterprise = enterprise;
        this.dataRepository = dataRepository;
    }

    @Override
    public int compareTo(Enterprise o) {
        EnterpriseData enterpriseData = dataRepository.findById(enterprise.getId()).orElse(null);
        if (enterpriseData == null) {
            enterpriseData = EnterpriseData.builder()
                    .id(UUID.randomUUID())
                    .enterpriseId(enterprise.getId())
                    .viewsNumbers(1L)
                    .build();
            dataRepository.save(enterpriseData);
        }
        EnterpriseData oData = dataRepository.findById(o.getId()).orElse(null);
        if (oData == null) {
            oData = EnterpriseData.builder()
                    .id(UUID.randomUUID())
                    .enterpriseId(o.getId())
                    .viewsNumbers(1L)
                    .build();
            dataRepository.save(oData);
        }
        return (int) (enterpriseData.getViewsNumbers() - oData.getViewsNumbers());
    }
}