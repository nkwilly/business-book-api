package com.business.book.repository;

import com.business.book.entity.Enterprise;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnterpriseRepository extends CassandraRepository<Enterprise, UUID> {
    
    List<Enterprise> findAllByBusinessActorId(UUID businessActorId);

    Enterprise findByIdAndBusinessActorId(UUID id, UUID businessActorId);
}
