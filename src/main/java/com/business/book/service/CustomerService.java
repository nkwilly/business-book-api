package com.business.book.service;

import com.business.book.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
    void deleteById(UUID id);
    void createEnterprise(UUID customerId);
    void deleteEnterprise(UUID customerId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}