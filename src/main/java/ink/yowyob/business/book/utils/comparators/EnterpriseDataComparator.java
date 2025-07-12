package ink.yowyob.business.book.utils.comparators;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.EnterpriseData;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import ink.yowyob.business.book.utils.GenerateUtils;
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
        EnterpriseData enterpriseData = dataRepository.findById(enterprise.getOrganizationId()).block();
        if (enterpriseData == null) {
            enterpriseData.setEnterpriseId(GenerateUtils.generateId());
            enterpriseData.setEnterpriseId(enterprise.getOrganizationId());
            enterpriseData.setViewsNumbers(1L);
            dataRepository.save(enterpriseData);
        }
        EnterpriseData oData = dataRepository.findById(o.getOrganizationId()).block();
        if (oData == null) {
            oData = new EnterpriseData();
            oData.setEnterpriseId(GenerateUtils.generateId());
            oData.setEnterpriseId(o.getOrganizationId());
            oData.setViewsNumbers(1L);
            dataRepository.save(oData);
        }
        return (int) (enterpriseData.getViewsNumbers() - oData.getViewsNumbers());
    }
}