package ink.yowyob.business.book.infrastructure.repository;

import ink.yowyob.business.book.infrastructure.entity.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface RoleRepository extends ReactiveCassandraRepository<Role, UUID> {
    Role findByName(String name);
}
