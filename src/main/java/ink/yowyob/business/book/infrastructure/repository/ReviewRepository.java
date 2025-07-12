package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.Review;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ReviewRepository extends ReactiveCassandraRepository<Review, UUID> {
    Flux<Review> findByOrganizationId(UUID organizationId);
}