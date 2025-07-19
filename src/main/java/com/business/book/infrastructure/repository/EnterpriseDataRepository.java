package com.business.book.infrastructure.repository;

import com.business.book.infrastructure.entity.EnterpriseData;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface EnterpriseDataRepository extends ReactiveCassandraRepository<EnterpriseData, UUID> {
    /*
    Mono<EnterpriseData> findByEnterpriseId(UUID enterpriseId);

    Flux<EnterpriseData> findAllByEnterpriseIdIn(List<UUID> enterpriseIds);

    default void incrementView(UUID enterpriseId) {
        Optional<EnterpriseData> data = findByEnterpriseId(enterpriseId);
        if (data.isPresent()) {
            EnterpriseData enterpriseData = data.get();
            enterpriseData.setViewsNumbers(enterpriseData.getViewsNumbers() + 1);
            save(enterpriseData);
            return;
        }
        EnterpriseData newData = new EnterpriseData();
        newData.setEnterpriseId(enterpriseId);
        newData.setViewsNumbers(1L);
        save(newData);
    }*/
}