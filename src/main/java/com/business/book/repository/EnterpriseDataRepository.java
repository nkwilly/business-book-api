package com.business.book.repository;

import com.business.book.entity.EnterpriseData;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnterpriseDataRepository extends CassandraRepository<EnterpriseData, UUID> {
    Optional<EnterpriseData> findByEnterpriseId(UUID enterpriseId);

    List<EnterpriseData> findAllByEnterpriseIdIn(List<UUID> enterpriseIds);

    default Optional<EnterpriseData> incrementView(UUID enterpriseId) {
        Optional<EnterpriseData> data = findByEnterpriseId(enterpriseId);
        if (data.isPresent()) {
            data.get().setViewsNumbers(data.get().getViewsNumbers() + 1);
            return data;
        }
        return Optional.empty();
    }
}