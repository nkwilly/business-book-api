package com.business.book.service;

import com.business.book.entity.SuperAdmin;
import com.business.book.entity.Traffic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SuperAdminService {
    SuperAdmin save(SuperAdmin superAdmin);
    List<SuperAdmin> findAll();
    Optional<SuperAdmin> findById(UUID id);
    Optional<SuperAdmin> findByEmail(String email);
    Optional<SuperAdmin> findByUsername(String username);
    void deleteById(UUID id);
    void hinderEnterprise(UUID superAdminId, String enterpriseId);
    void hinderCustomer(UUID superAdminId, String customerId);
    List<Traffic> getAllTraffics(UUID superAdminId);
}