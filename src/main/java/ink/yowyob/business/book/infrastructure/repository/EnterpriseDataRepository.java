package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.EnterpriseData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnterpriseDataRepository extends ReactiveCassandraRepository<EnterpriseData, UUID> {
    Mono<EnterpriseData> findByEnterpriseId(UUID enterpriseId);

    Flux<EnterpriseData> findAllByEnterpriseIdIn(List<UUID> enterpriseIds);
}