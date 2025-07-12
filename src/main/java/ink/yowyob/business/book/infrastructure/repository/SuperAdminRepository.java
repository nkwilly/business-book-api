package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.SuperAdmin;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SuperAdminRepository extends ReactiveCassandraRepository<SuperAdmin, UUID> {
    Optional<SuperAdmin> findByEmail(String email);
    Optional<SuperAdmin> findByUsername(String username);
}