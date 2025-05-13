package com.business.book.repository;

import com.business.book.entity.Customer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}